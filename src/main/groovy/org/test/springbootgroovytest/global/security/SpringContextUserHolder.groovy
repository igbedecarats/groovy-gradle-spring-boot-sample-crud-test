package org.test.springbootgroovytest.global.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.test.springbootgroovytest.users.domain.User

class SpringContextUserHolder {

    static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
        return (User) authentication.getPrincipal()
    }

    static Token getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
        return (Token) authentication.getCredentials()
    }
}
