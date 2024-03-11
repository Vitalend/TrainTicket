package ru.train.ticket.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.train.ticket.DTO.GroupTicketDTO;
import ru.train.ticket.services.GroupTicketService;
import ru.train.ticket.util.exceptions.GroupTicketBuyException;
import ru.train.ticket.util.exceptions.TicketErrorResponse;

@RestController
@RequestMapping("/ticket")
public class GroupTicketController {

    GroupTicketService groupTicketService;

    @Autowired
    public GroupTicketController(GroupTicketService groupTicketService) {
        this.groupTicketService = groupTicketService;
    }

    @PostMapping("/buy/group")
    public String groupBuy(@Valid @RequestBody GroupTicketDTO groupTicketDTO) {
        return groupTicketService.groupTicketBuy(groupTicketDTO);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(GroupTicketBuyException e) {
        TicketErrorResponse response = new TicketErrorResponse(
                "Выбранное количество билетов недоступно к покупке");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

