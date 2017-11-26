package org.test.springbootgroovytest.users.usecase

import org.test.springbootgroovytest.users.domain.User

class CreateUserRequest {
    String username
    String password
    String firstName
    String lastName
    String email

    User ToUser() {
        new User(username: username, password: password, firstName: firstName, lastName: lastName, email: email)
    }
}
