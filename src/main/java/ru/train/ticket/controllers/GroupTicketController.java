package ru.train.ticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.train.ticket.models.GroupTicket;
import ru.train.ticket.services.GroupTicketService;
import ru.train.ticket.util.exceptions.GroupTicketBuyException;
import ru.train.ticket.util.exceptions.TicketErrorResponse;

import java.sql.SQLException;

@RestController
@RequestMapping("/ticket")
public class GroupTicketController {

    GroupTicketService groupTicketService;

    @Autowired
    public GroupTicketController(GroupTicketService groupTicketService) {
        this.groupTicketService = groupTicketService;
    }

    @PatchMapping("/buy/group")
    public String groupBuy(@RequestBody GroupTicket groupTicket) throws SQLException {
        return groupTicketService.groupTicketBuy(groupTicket);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(GroupTicketBuyException e) {
        TicketErrorResponse response = new TicketErrorResponse(
                "Выбранное количество билетов недоступно к покупке");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

