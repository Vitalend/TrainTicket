package ru.train.ticket.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.train.ticket.models.SeatTicket;
import ru.train.ticket.util.ConnectionToDB;

import java.sql.*;

@Component
public class SeatTicketDAO {

    Environment environment;

    ConnectionToDB connectionToDB;
    private String response;

    @Autowired
    public SeatTicketDAO(Environment environment, ConnectionToDB connectionToDB) {
        this.environment = environment;
        this.connectionToDB = connectionToDB;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Boolean buyOneTicket(SeatTicket seatTicket) {
        try {
            PreparedStatement preparedStatement = connectionToDB.connect
                    ("SELECT * FROM Trains JOIN Wagons "
                            + "ON Trains.train_id = Wagons.train_id JOIN Seats "
                            + "ON Wagons.wagon_id = Seats.wagon_id WHERE train_number = ?"
                            + " AND wagon_number = ? AND seat_number = ? AND seat_buying = true");
            preparedStatement.setInt(1, seatTicket.getTrainNumber());
            preparedStatement.setInt(2, seatTicket.getWagonNumber());
            preparedStatement.setInt(3, seatTicket.getSeatNumber());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                connectionToDB.update("UPDATE seats SET seat_buying = false WHERE seat_id = "
                        + resultSet.getInt("seat_id"));

                response = "Билет на поезд " + seatTicket.getTrainNumber() + " в вагон "
                        + seatTicket.getWagonNumber() + " на место " + seatTicket.getSeatNumber() +
                        " куплен, стоимость " + resultSet.getDouble("seat_cost") +
                        " тип вагона " + resultSet.getString("wagon_type");
                resultSet.close();
                return true;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public Boolean groupTicketBuy(SeatTicket seatTicket) {
        int counter = 0;
        try {
            PreparedStatement preparedStatement = connectionToDB.connect
                    ("SELECT * FROM Trains JOIN Wagons ON Trains.train_id = Wagons.train_id " +
                            "JOIN Seats ON Wagons.wagon_id = Seats.wagon_id WHERE seat_buying = true " +
                            "AND train_number = ?");
            preparedStatement.setInt(1, seatTicket.getTrainNumber());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                counter++;
            }
            preparedStatement.close();
            int quantity = seatTicket.getTicketQuantity();
            if (counter >= quantity) {

                preparedStatement = connectionToDB.connect
                        ("SELECT * FROM Trains JOIN Wagons ON Trains.train_id = Wagons.train_id " +
                                "JOIN Seats ON Wagons.wagon_id = Seats.wagon_id WHERE seat_buying = true " +
                                "AND train_number = ?");
                preparedStatement.setInt(1, seatTicket.getTrainNumber());
                ResultSet resultSet2 = preparedStatement.executeQuery();
                while (resultSet2.next()) {
                    if (quantity > 0) {
                        connectionToDB.update("UPDATE seats SET seat_buying = false WHERE seat_id = "
                                + resultSet2.getInt("seat_id"));
                        quantity--;
                    }
                }
                resultSet2.close();
                response = "Билеты на поезд " + seatTicket.getTrainNumber() +
                        " куплены в количестве " + seatTicket.getTicketQuantity();
                return true;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public Boolean refundTicket(SeatTicket seatTicket) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp twoHours = new Timestamp(7200000);

        try {
            PreparedStatement preparedStatement = connectionToDB.connect
                    ("SELECT * FROM Trains JOIN Wagons "
                            + "ON Trains.train_id = Wagons.train_id JOIN Seats "
                            + "ON Wagons.wagon_id = Seats.wagon_id WHERE train_number = ?"
                            + " AND wagon_number = ? AND seat_number = ? ");
            preparedStatement.setInt(1, seatTicket.getTrainNumber());
            preparedStatement.setInt(2, seatTicket.getWagonNumber());
            preparedStatement.setInt(3, seatTicket.getSeatNumber());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Timestamp timestampDB = (resultSet.getTimestamp("train_departure"));
                if (!resultSet.getBoolean("seat_buying")
                        && (timestampDB.getTime() - timestamp.getTime()) > twoHours.getTime()) {

                    connectionToDB.update("UPDATE seats SET seat_buying = true WHERE seat_id = "
                            + resultSet.getInt("seat_id"));
                    response = "Успешный возврат билета на поезд " + seatTicket.getTrainNumber() + " вагон "
                            + seatTicket.getWagonNumber() + " место " + seatTicket.getSeatNumber()
                            + " средства вернутся в размере " + resultSet.getInt("seat_cost");
                    resultSet.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
