package br.com.robertomassoni.mancala.core.exception;

import org.springframework.http.HttpStatus;

public class InvalidMoveException extends ApiException {

    public InvalidMoveException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
