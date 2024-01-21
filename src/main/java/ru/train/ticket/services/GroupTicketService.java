package ru.train.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.train.ticket.util.ConnectionToDB;
import ru.train.ticket.util.exceptions.GroupTicketBuyException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ru.train.ticket.models.GroupTicket;

@Component
public class GroupTicketService {

    ConnectionToDB connectionToDB;

    @Autowired
    public GroupTicketService(ConnectionToDB connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public String groupTicketBuy(GroupTicket groupTicket) throws SQLException {

        List<Integer> seatIdList = new ArrayList<>();

        int counter = groupTicket.getTicketQuantity();

        connectionToDB.getConnection().setAutoCommit(false);
        connectionToDB.getConnection().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        PreparedStatement getJoinData = connectionToDB.getConnection().prepareStatement
                ("SELECT * FROM Trains JOIN Wagons ON Trains.train_id = Wagons.train_id " +
                        "JOIN Seats ON Wagons.wagon_id = Seats.wagon_id WHERE train_number = ? " +
                        "AND wagon_number = ? AND seat_buying = true");

        getJoinData.setInt(1, groupTicket.getTrainNumber());
        getJoinData.setInt(2, groupTicket.getWagonNumber());

        ResultSet resultSet = getJoinData.executeQuery();
        while (resultSet.next()) {
            seatIdList.add(resultSet.getInt("seat_id"));
        }
        if (seatIdList.size() >= counter) {

            for (int i = 0; i < counter; i++) {

                PreparedStatement updateSeats = connectionToDB.getConnection().prepareStatement
                        ("UPDATE seats SET seat_buying = FALSE WHERE seat_id = " + seatIdList.get(i));
                updateSeats.executeUpdate();
            }
            connectionToDB.getConnection().commit();
            connectionToDB.getConnection().setAutoCommit(true);
            connectionToDB.getConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            return "Билеты на поезд " + groupTicket.getTrainNumber() + " в вагон " + groupTicket.getWagonNumber() +
                    " куплены в количестве " + groupTicket.getTicketQuantity();
        }
        connectionToDB.getConnection().rollback();
        connectionToDB.getConnection().setAutoCommit(true);
        connectionToDB.getConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        throw new GroupTicketBuyException();
    }
}


