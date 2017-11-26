package org.test.springbootgroovytest.global.web

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

class HttpApiError {

    private HttpStatus status

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message

    HttpApiError(final HttpStatus status, final String message) {
        this.status = status
        this.message = message
    }

    static HttpApiError create(final HttpStatus status, final String message) {
        return createWithErrors(status, message)
    }

    static HttpApiError createWithErrors(
            final HttpStatus status, final String message) {
        return new HttpApiError(status, message)
    }

    HttpStatus getStatus() {
        return status
    }

    String getMessage() {
        return message
    }
}
