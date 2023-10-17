package br.com.robertomassoni.mancala.core.exception;

import org.springframework.http.HttpStatus;

public class PlayerNotFoundException extends ApiException {

    public PlayerNotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public PlayerNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
