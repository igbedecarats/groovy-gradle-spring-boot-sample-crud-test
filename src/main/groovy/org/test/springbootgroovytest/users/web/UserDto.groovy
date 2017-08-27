package org.test.springbootgroovytest.users.web

import org.test.springbootgroovytest.users.domain.User

class UserDto {
    String username
    String firstName
    String lastName

    User ToUser() {
        new User(username: username, firstName: firstName, lastName: lastName)
    }
}
