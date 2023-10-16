package br.com.robertomassoni.mancala.core.exception;

import org.springframework.http.HttpStatus;

public class PitNotFoundException extends ApiException {

    public PitNotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public PitNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
