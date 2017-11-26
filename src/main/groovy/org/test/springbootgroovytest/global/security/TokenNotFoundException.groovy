package org.test.springbootgroovytest.global.security

import org.springframework.security.core.AuthenticationException

class TokenNotFoundException extends AuthenticationException {

    TokenNotFoundException(final String msg) {
        super(msg)
    }
}
