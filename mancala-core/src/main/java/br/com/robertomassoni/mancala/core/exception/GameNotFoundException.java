package br.com.robertomassoni.mancala.core.exception;

import org.springframework.http.HttpStatus;

public class GameNotFoundException extends ApiException {

    public GameNotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
