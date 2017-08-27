package org.test.springbootgroovytest.global.web

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import javax.persistence.EntityNotFoundException

/**
 * This class handles exceptions raised in any part of the application
 */
@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    ResponseEntity<?> handleEntityNotFound(final Exception ex) {
        return logAndReturnResponseEntityForError(ex, HttpStatus.NOT_FOUND)
    }

    private ResponseEntity<?> logAndReturnResponseEntityForError(final Exception ex,
                                                                 final HttpStatus httpStatus) {
        logger.error(ex.getMessage(), ex.getCause());
        return new ResponseEntity<Object>(httpStatus);
    }
}
