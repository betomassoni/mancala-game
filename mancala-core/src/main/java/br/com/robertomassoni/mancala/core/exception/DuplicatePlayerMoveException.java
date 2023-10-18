package br.com.robertomassoni.mancala.core.exception;

import org.springframework.http.HttpStatus;

public class DuplicatePlayerMoveException extends ApiException {

    public DuplicatePlayerMoveException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public DuplicatePlayerMoveException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
