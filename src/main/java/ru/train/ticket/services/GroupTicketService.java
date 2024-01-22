package ru.train.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.train.ticket.util.ConnectionTB;
import ru.train.ticket.util.exceptions.GroupTicketBuyException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ru.train.ticket.models.GroupTicket;

@Component
public class GroupTicketService {

    ConnectionTB connectionTB;

    @Autowired
    public GroupTicketService(ConnectionTB connectionTB) {
        this.connectionTB = connectionTB;
    }

    public String groupTicketBuy(GroupTicket groupTicket) throws SQLException {

        Connection con = connectionTB.connect();

        con.setAutoCommit(false);

        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        List<Integer> seatIdList = new ArrayList<>();

        int counter = groupTicket.getTicketQuantity();

        PreparedStatement getJoinData = con.prepareStatement
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
                con.prepareStatement("UPDATE seats SET seat_buying = FALSE " +
                        "WHERE seat_id = " + seatIdList.get(i)).execute();

            }
            con.commit();
            con.setAutoCommit(true);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.close();

            return "Билеты на поезд " + groupTicket.getTrainNumber() + " в вагон "
                    + groupTicket.getWagonNumber() + " куплены в количестве "
                    + groupTicket.getTicketQuantity();
        }
        con.rollback();
        con.setAutoCommit(true);
        con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        resultSet.close();
        getJoinData.close();
        con.close();
        throw new GroupTicketBuyException();
    }
}


