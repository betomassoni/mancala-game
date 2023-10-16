package br.com.robertomassoni.mancala.core.exception.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ExceptionMessage {
    private final HttpStatus status;
    private final String timestamp;
    private final String message;
    private ValidationError validationError;

    public ExceptionMessage(HttpStatus status, String message) {
        this.status = status;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.message = message;
    }

    public ExceptionMessage(HttpStatus status, String message, ValidationError validationError) {
        this.status = status;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.message = message;
        this.validationError = validationError;
    }
}
