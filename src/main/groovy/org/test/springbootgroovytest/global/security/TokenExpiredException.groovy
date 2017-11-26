package org.test.springbootgroovytest.global.security

import org.springframework.security.core.AuthenticationException

class TokenExpiredException extends AuthenticationException {

    TokenExpiredException(final String msg) {
        super(msg)
    }
}
