package ru.train.ticket.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.train.ticket.services.OneTicketService;
import ru.train.ticket.DTO.OneTicketDTO;
import ru.train.ticket.util.BindingResultMessage;
import ru.train.ticket.util.exceptions.DTOValidationException;
import ru.train.ticket.util.exceptions.TicketErrorResponse;
import ru.train.ticket.util.exceptions.OneTicketBuyException;
import ru.train.ticket.util.exceptions.TicketRefundException;

@RestController
@RequestMapping("/ticket")
public class OneTicketController {

    OneTicketService oneTicketService;
    BindingResultMessage bindingResultMessage;

    @Autowired
    public OneTicketController(OneTicketService oneTicketService, BindingResultMessage bindingResultMessage) {
        this.oneTicketService = oneTicketService;
        this.bindingResultMessage = bindingResultMessage;
    }

    @PostMapping("/buy/one")
    public String ticketBuying(@Valid @RequestBody OneTicketDTO oneTicketDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            throw new DTOValidationException(bindingResultMessage.getErrorMessage(bindingResult));
        }
        return oneTicketService.buyOneTicket(oneTicketDTO);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(OneTicketBuyException e) {
        TicketErrorResponse response = new TicketErrorResponse(
                "Выбранный билет недоступен к покупке, выберите другой поезд, вагон или место");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(DTOValidationException e) {
        TicketErrorResponse response = new TicketErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refund")
    public String tickerRefund(@Valid @RequestBody OneTicketDTO oneTicketDTO) {
        return oneTicketService.refundTicket(oneTicketDTO);
    }

    @ExceptionHandler
    private ResponseEntity<TicketErrorResponse> handleException(TicketRefundException e) {
        TicketErrorResponse response = new TicketErrorResponse(
                "Выбранный билет недоступен к возврату, выберите другой билет");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
