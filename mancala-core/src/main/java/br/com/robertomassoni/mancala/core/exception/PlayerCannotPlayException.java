package br.com.robertomassoni.mancala.core.exception;

import org.springframework.http.HttpStatus;

public class PlayerCannotPlayException extends ApiException {

    public PlayerCannotPlayException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
