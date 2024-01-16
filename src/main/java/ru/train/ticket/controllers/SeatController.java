package ru.train.ticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import ru.train.ticket.DAO.SeatDAO;
import ru.train.ticket.DAO.SeatDAOTicket;
import ru.train.ticket.models.SeatTicket;

@RestController
@RequestMapping("/ticket")
public class SeatController {

    SeatDAOTicket seatDAOTicket;

    @Autowired
    public SeatController(SeatDAOTicket seatDAOTicket) {
        this.seatDAOTicket = seatDAOTicket;
    }

    @PatchMapping("/buy/one")
    public String ticketBuying(@RequestBody SeatTicket seatTicket) {

        if (seatDAOTicket.buyOneTicket(seatTicket)){
           seatDAOTicket.buyOneTicket(seatTicket);
            return seatDAOTicket.getResponse();
        }
        return "Выбранный билет недоступен к покупке, выберите другой поезд, вагон или место";
    }

    @PatchMapping("/buy/group")
    public String groupTicketBuying(@RequestBody SeatTicket seatTicket){

        if (seatDAOTicket.groupTicketBuy(seatTicket)){
            seatDAOTicket.groupTicketBuy(seatTicket);
            return seatDAOTicket.getResponse();
        }
        return "Выбранное количество билетов недоступно к покупке, выберите другой поезд";
    }

    @PatchMapping("/refund")
    public String tickerRefund(@RequestBody SeatTicket seatTicket){
        if (seatDAOTicket.refundTicket(seatTicket)){
            seatDAOTicket.refundTicket(seatTicket);
            return seatDAOTicket.getResponse();
        }
        return "Билет недоступен к возврату";
    }
}
