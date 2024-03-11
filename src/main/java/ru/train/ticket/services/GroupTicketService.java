package ru.train.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.train.ticket.util.exceptions.GroupTicketBuyException;

import java.util.List;
import java.util.Map;

import ru.train.ticket.DTO.GroupTicketDTO;

@Component
public class GroupTicketService {

    private final JdbcClient jdbcClient;

    @Autowired
    public GroupTicketService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String groupTicketBuy(GroupTicketDTO groupTicketDTO) {

        int counter = 0;
        int ticketQuantity = groupTicketDTO.getTicketQuantity();

        String getId = "SELECT seat_id FROM Trains JOIN Departures ON Trains.train_id = Departures.train_id " +
                " JOIN Wagons ON Wagons.departure_id = Departures.departure_id " +
                " JOIN Seats ON Wagons.wagon_id = Seats.wagon_id " +
                " WHERE train_number = ? AND departure_time = ? AND wagon_number = ? AND seat_occupied = true";

        List<Map<String, Object>> seatIdList = jdbcClient.sql(getId)
                .param(1, groupTicketDTO.getTrainNumber())
                .param(2, groupTicketDTO.getDepartureTime())
                .param(3, groupTicketDTO.getWagonNumber())
                .query()
                .listOfRows();
        if (ticketQuantity <= seatIdList.size()) {

            for (int i = 0; i < ticketQuantity; i++) {

                Object seatId = seatIdList.get(i).get("seat_id");

                String update = "UPDATE seats SET seat_occupied = false " +
                        "WHERE seat_id = " + seatId;
                int countUpdate = jdbcClient.sql(update).update();
                if (countUpdate != 1) {
                    throw new GroupTicketBuyException();
                }
                String insert = "INSERT INTO passengers (seat_id, passenger_name, passport_number)" +
                        "VALUES (" + seatId + ", ?, ?)";

                int countInsert = jdbcClient.sql(insert)
                        .param(1, groupTicketDTO.getPassengerName())
                        .param(2, groupTicketDTO.getPassportNumber())
                        .update();
                 if (countInsert != 1) {
                    throw new GroupTicketBuyException();
                }
                counter++;
            }
        }
        if (counter != ticketQuantity) {
            throw new GroupTicketBuyException();
        }
        return "Билеты на поезд " + groupTicketDTO.getTrainNumber() + " в вагон "
                + groupTicketDTO.getWagonNumber() + " куплены в количестве "
                + groupTicketDTO.getTicketQuantity();
    }
}



