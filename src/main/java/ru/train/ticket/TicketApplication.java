package ru.train.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.TimeZone;


@SpringBootApplication
public class TicketApplication {

    public static void main(String[] args) {

        System.setProperty("user.timezone", "Etc/GMT+0");
//
//        TimeZone.setDefault(TimeZone.getDefault());
//
//        String[] timeZoneList = TimeZone.getAvailableIDs();
//
//        for (String t : timeZoneList) {
//            System.out.println(t);
//
//        }


        SpringApplication.run(TicketApplication.class, args);

    }

}
