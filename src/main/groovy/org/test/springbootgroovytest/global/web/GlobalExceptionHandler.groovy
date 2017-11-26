package org.test.springbootgroovytest.global.web

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import javax.persistence.EntityNotFoundException

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    ResponseEntity<?> handleEntityNotFound(final Exception ex) {
        return logAndReturnResponseEntityForError(ex, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler([IllegalArgumentException.class, NullPointerException.class])
    @ResponseBody
    ResponseEntity<?> handleBadRequests(final Exception ex) {
        return logAndReturnResponseEntityForError(ex, HttpStatus.BAD_REQUEST)
    }

    private ResponseEntity<?> logAndReturnResponseEntityForError(final Exception ex,
                                                                 final HttpStatus httpStatus) {
        logger.error(ex.getMessage(), ex.getCause())
        HttpApiError error = new HttpApiError(httpStatus, ex.getMessage())
        return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus())
    }

    ResponseEntity<HttpApiError> toResponseEntity(final Throwable throwable, final HttpStatus httpStatus) {
        String errorMessage = throwable.getMessage()
        return create(errorMessage, throwable, httpStatus)
    }

    private ResponseEntity<HttpApiError> create(final String errorMessage, final Throwable throwable,
                                                final HttpStatus httpStatus) {
        logger.error(throwable.getMessage(), throwable)
        HttpApiError error = HttpApiError.create(httpStatus, errorMessage)
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus())
    }
}
