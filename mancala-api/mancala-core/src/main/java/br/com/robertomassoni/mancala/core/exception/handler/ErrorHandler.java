package br.com.robertomassoni.mancala.core.exception.handler;

import br.com.robertomassoni.mancala.core.exception.ApiException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private static final String GENERAL_ERROR_MESSAGE = "Internal server error";
    private static final String API_ERROR_MESSAGE = "API internal error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleException(final Exception ex) {
        final var message = Strings.isNotEmpty(ex.getMessage()) ? ex.getMessage() : GENERAL_ERROR_MESSAGE;
        return new ResponseEntity(new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionMessage> handleApiException(final Exception ex) {
        final var httpStatus = ((ApiException) ex).getHttpStatus();
        final var message = Strings.isNotEmpty(ex.getMessage()) ? ex.getMessage() : API_ERROR_MESSAGE;
        return new ResponseEntity(new ExceptionMessage(httpStatus, message), httpStatus);
    }
}
