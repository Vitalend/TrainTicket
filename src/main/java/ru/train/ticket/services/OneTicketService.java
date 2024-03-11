package ru.train.ticket.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.train.ticket.DTO.OneTicketDTO;
import ru.train.ticket.util.exceptions.OneTicketBuyException;
import ru.train.ticket.util.exceptions.TicketRefundException;

import java.sql.*;
import java.time.LocalDateTime;

@Component
public class OneTicketService {
    private final JdbcClient jdbcClient;

    @Autowired
    public OneTicketService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    private static final int TWO_HOURS_BEFORE_DEPARTURE = 5;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String buyOneTicket(@Valid OneTicketDTO oneTicketDTO) {

        String update = "UPDATE seats SET seat_occupied = false WHERE seat_id= " +
                "(SELECT seat_id FROM Trains " +
                "JOIN departures ON Trains.train_id = departures.train_id " +
                "JOIN Wagons ON departures.departure_id = wagons.departure_id " +
                "JOIN Seats ON Wagons.wagon_id = Seats.wagon_id " +
                "WHERE train_number = ? AND departure_time = ? AND wagon_number = ? " +
                "AND seat_number = ? AND seat_occupied = true)";

        String getId = "SELECT seat_id FROM Trains JOIN Departures ON Trains.train_id = Departures.train_id " +
                " JOIN Wagons ON Departures.departure_id = Wagons.departure_id " +
                " JOIN Seats ON Wagons.wagon_id = Seats.wagon_id " +
                " WHERE train_number = ? AND departure_time = ? AND wagon_number = ? " +
                " AND seat_number = ? ";

        int countUpdate = jdbcClient.sql(update)
                .param(1, oneTicketDTO.getTrainNumber())
                .param(2, oneTicketDTO.getDepartureTime())
                .param(3, oneTicketDTO.getWagonNumber())
                .param(4, oneTicketDTO.getSeatNumber())
                .update();
        if (countUpdate != 1) {
            throw new OneTicketBuyException();
        }

        Object seatId = jdbcClient.sql(getId)
                .param(1, oneTicketDTO.getTrainNumber())
                .param(2, oneTicketDTO.getDepartureTime())
                .param(3, oneTicketDTO.getWagonNumber())
                .param(4, oneTicketDTO.getSeatNumber())
                .query()
                .singleValue();

        String insert = "INSERT INTO passengers (seat_id, passenger_name, passport_number)" +
                "VALUES (" + seatId + ", ?, ?)";

        int countCreate = jdbcClient.sql(insert)
                .param(1, oneTicketDTO.getPassengerName())
                .param(2, oneTicketDTO.getPassportNumber())
                .update();
        if (countCreate != 1) {
            throw new OneTicketBuyException();
        }
        return "Билет на поезд " + oneTicketDTO.getTrainNumber() + " в вагон "
                + oneTicketDTO.getWagonNumber() + " на место " + oneTicketDTO.getSeatNumber() +
                " время отправления " + oneTicketDTO.getDepartureTime() + " куплен. ";
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String refundTicket(OneTicketDTO oneTicketDTO) {

        String update = "UPDATE seats SET seat_occupied = true WHERE seat_id= " +
                "(SELECT seat_id FROM Trains " +
                "JOIN departures ON Trains.train_id = departures.train_id " +
                "JOIN Wagons ON departures.departure_id = wagons.departure_id " +
                "JOIN Seats ON Wagons.wagon_id = Seats.wagon_id " +
                "WHERE train_number = ? AND departure_time = ? AND wagon_number = ? " +
                "AND seat_number = ? AND seat_occupied = false AND departure_time > ?)";

        String delete = "DELETE FROM Passengers WHERE seat_id = (SELECT seat_id FROM Trains" +
                " JOIN Departures ON Trains.train_id = Departures.train_id" +
                " JOIN Wagons ON Departures.departure_id = Wagons.departure_id" +
                " JOIN Seats ON Wagons.wagon_id = Seats.wagon_id" +
                " AND train_number = ? AND departure_time = ? AND wagon_number = ? AND seat_number = ?)";

        int countUpdate = jdbcClient.sql(update)
                .param(1, oneTicketDTO.getTrainNumber())
                .param(2, oneTicketDTO.getDepartureTime())
                .param(3, oneTicketDTO.getWagonNumber())
                .param(4, oneTicketDTO.getSeatNumber())
                .param(5, Timestamp.valueOf(LocalDateTime.now().plusHours(TWO_HOURS_BEFORE_DEPARTURE)))
                .update();

        if (countUpdate != 1) {
            throw new TicketRefundException();
        }

        int countDelete = jdbcClient.sql(delete)
                .param(1, oneTicketDTO.getTrainNumber())
                .param(2, oneTicketDTO.getDepartureTime())
                .param(3, oneTicketDTO.getWagonNumber())
                .param(4, oneTicketDTO.getSeatNumber())
                .update();

        if (countDelete != 1) {
            throw new TicketRefundException();
        }
        return "Билет на поезд " + oneTicketDTO.getTrainNumber() + " в вагон "
                + oneTicketDTO.getWagonNumber() + " на место " + oneTicketDTO.getSeatNumber() +
                " успешно возвращен ";
    }
}




