package org.test.springbootgroovytest.users.domain

import org.springframework.security.core.GrantedAuthority

enum UserRole implements GrantedAuthority {

    USER, ADMIN

    @Override
    String getAuthority() {
        return "ROLE_" + name()
    }
}
