package ru.train.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.train.ticket.models.OneTicket;
import ru.train.ticket.util.ConnectionTB;
import ru.train.ticket.util.exceptions.OneTicketBuyException;
import ru.train.ticket.util.exceptions.TicketRefundException;


import java.sql.*;
import java.time.LocalDateTime;

@Component
public class OneTicketService {

   ConnectionTB connectionTB;

   @Autowired
    public OneTicketService(ConnectionTB connectionTB) {
        this.connectionTB = connectionTB;
    }

    private static final int TWO_HOURS_BEFORE_DEPARTURE = 2;


    public String buyOneTicket(OneTicket oneTicket) throws SQLException {

        try (PreparedStatement updateForBuy = connectionTB.connect().prepareStatement(
                "UPDATE seats SET seat_buying = false WHERE seat_id= " +
                        "(SELECT seat_id FROM Trains JOIN Wagons" +
                        "    ON Trains.train_id = Wagons.train_id JOIN Seats" + "    ON Wagons.wagon_id = Seats.wagon_id WHERE trains.train_number = ?" +
                       "    AND wagon_number = ? AND seat_number = ? AND seat_buying = true);")) {

            updateForBuy.setInt(1, oneTicket.getTrainNumber());
            updateForBuy.setInt(2, oneTicket.getWagonNumber());
            updateForBuy.setInt(3, oneTicket.getSeatNumber());

            if (updateForBuy.executeUpdate() == 1) {
                connectionTB.connect().close();
                return "Билет на поезд " + oneTicket.getTrainNumber() + " в вагон "
                        + oneTicket.getWagonNumber() + " на место " + oneTicket.getSeatNumber() +
                        " куплен. ";
            }
        }
        connectionTB.connect().close();
        throw new OneTicketBuyException();
    }

    public String refundTicket(OneTicket oneTicket) throws SQLException {

        try (PreparedStatement updateForRefund = connectionTB.connect().prepareStatement(
                "UPDATE seats SET seat_buying = true WHERE seat_id= " +
                        "(SELECT seat_id FROM Trains JOIN Wagons" +
                        "    ON Trains.train_id = Wagons.train_id JOIN Seats" +
                        "    ON Wagons.wagon_id = Seats.wagon_id WHERE trains.train_number = ?" +
                        "    AND wagon_number = ? AND seat_number = ? AND seat_buying = false " +
                        "AND train_departure > ?);")) {

            updateForRefund.setInt(1, oneTicket.getTrainNumber());
            updateForRefund.setInt(2, oneTicket.getWagonNumber());
            updateForRefund.setInt(3, oneTicket.getSeatNumber());
            updateForRefund.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now().
                   plusHours(TWO_HOURS_BEFORE_DEPARTURE)));

            if (updateForRefund.executeUpdate() == 1) {
                return "Билет на поезд " + oneTicket.getTrainNumber() + " в вагон "
                        + oneTicket.getWagonNumber() + " на место " + oneTicket.getSeatNumber() +
                        " успешно возвращен ";
            }
        }
        throw new TicketRefundException();
    }
}




