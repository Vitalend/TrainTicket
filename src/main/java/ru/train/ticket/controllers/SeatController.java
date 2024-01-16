package ru.train.ticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.train.ticket.DAO.SeatTicketDAO;
import ru.train.ticket.models.SeatTicket;

@RestController
@RequestMapping("/ticket")
public class SeatController {

    SeatTicketDAO seatTicketDAO;

    @Autowired
    public SeatController(SeatTicketDAO seatTicketDAO) {
        this.seatTicketDAO = seatTicketDAO;
    }

    @PatchMapping("/buy/one")
    public String ticketBuying(@RequestBody SeatTicket seatTicket) {

        if (seatTicketDAO.buyOneTicket(seatTicket)) {
            seatTicketDAO.buyOneTicket(seatTicket);
            return seatTicketDAO.getResponse();
        }
        return "Выбранный билет недоступен к покупке, выберите другой поезд, вагон или место";
    }

    @PatchMapping("/buy/group")
    public String groupTicketBuying(@RequestBody SeatTicket seatTicket) {

        if (seatTicketDAO.groupTicketBuy(seatTicket)) {
            seatTicketDAO.groupTicketBuy(seatTicket);
            return seatTicketDAO.getResponse();
        }
        return "Выбранное количество билетов недоступно к покупке, выберите другой поезд";
    }

    @PatchMapping("/refund")
    public String tickerRefund(@RequestBody SeatTicket seatTicket) {
        if (seatTicketDAO.refundTicket(seatTicket)) {
            seatTicketDAO.refundTicket(seatTicket);
            return seatTicketDAO.getResponse();
        }
        return "Билет недоступен к возврату";
    }
}
