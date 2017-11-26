package org.test.springbootgroovytest.users.usecase

import org.test.springbootgroovytest.users.domain.UserRepository

class CreateUserUsecase {

    private UserRepository userRepository

    CreateUserUsecase(final UserRepository userRepository) {
        this.userRepository = userRepository
    }

    def create(CreateUserRequest request) {
        userRepository.save(request.ToUser())
    }
}
