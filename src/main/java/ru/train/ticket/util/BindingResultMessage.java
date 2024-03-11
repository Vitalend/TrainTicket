package ru.train.ticket.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
@Component
public class BindingResultMessage {

    public String getErrorMessage(BindingResult bindingResult) {

        StringBuilder errorMessage = new StringBuilder();

        if (bindingResult.hasErrors()) {

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessage
                        .append(error.getField())
                        .append((" - "))
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
        }
        return errorMessage.toString();
    }
}
