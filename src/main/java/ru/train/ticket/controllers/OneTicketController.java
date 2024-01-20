package ru.train.ticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.train.ticket.services.OneTicketService;
import ru.train.ticket.models.OneTicket;
import ru.train.ticket.util.exceptions.TicketErrorResponse;
import ru.train.ticket.util.exceptions.OneTicketBuyException;
import ru.train.ticket.util.exceptions.TicketRefundException;

import java.sql.SQLException;

@RestController
@RequestMapping("/ticket")
public class OneTicketController {

    OneTicketService oneTicketService;

    @Autowired
    public OneTicketController(OneTicketService oneTicketService) {
        this.oneTicketService = oneTicketService;
    }

    @PatchMapping("/buy/one")
    public String ticketBuying(@RequestBody OneTicket oneTicket) throws SQLException {
        return oneTicketService.buyOneTicket(oneTicket);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(OneTicketBuyException e) {
        TicketErrorResponse response = new TicketErrorResponse(
                "Выбранный билет недоступен к покупке, выберите другой поезд, вагон или место");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/refund")
    public String tickerRefund(@RequestBody OneTicket oneTicket) throws SQLException {
        return oneTicketService.refundTicket(oneTicket);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(TicketRefundException e) {
        TicketErrorResponse response = new TicketErrorResponse(
                "Выбранный билет недоступен к возврату, выберите другой билет");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
