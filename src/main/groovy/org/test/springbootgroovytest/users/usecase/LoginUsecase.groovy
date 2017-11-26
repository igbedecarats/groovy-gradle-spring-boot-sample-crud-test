package org.test.springbootgroovytest.users.usecase

import org.test.springbootgroovytest.global.security.TokenProvider
import org.test.springbootgroovytest.users.domain.User
import org.test.springbootgroovytest.users.domain.UserRepository

import javax.persistence.EntityNotFoundException

class LoginUsecase {

    private UserRepository userRepository

    private TokenProvider provider

    LoginUsecase(final UserRepository userRepository, final TokenProvider provider) {
        this.userRepository = userRepository
        this.provider = provider
    }

    def login(final LoginRequest request) {
        User user = userRepository.findByUsernameAndPassword(request.username, request.password)
        if (user == null) {
            throw new EntityNotFoundException()
        }
        provider.provide(user.username)
    }
}
