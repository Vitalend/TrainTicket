package ru.train.ticket.util.exceptions;

public class TicketErrorResponse {

    private String errorMessage;

    public TicketErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
