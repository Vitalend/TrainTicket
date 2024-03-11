package ru.train.ticket.util.exceptions;

public class DTOValidationException extends RuntimeException{

    public DTOValidationException (String msg){
        super((msg));
    }
}
