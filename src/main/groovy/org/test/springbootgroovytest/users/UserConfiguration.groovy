package org.test.springbootgroovytest.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.test.springbootgroovytest.global.security.TokenProvider
import org.test.springbootgroovytest.users.domain.UserRepository
import org.test.springbootgroovytest.users.usecase.CreateUserUsecase
import org.test.springbootgroovytest.users.usecase.LoginUsecase

@Configuration
class UserConfiguration {

    @Autowired
    private UserRepository userRepository

    @Autowired
    private TokenProvider tokenProvider

    @Bean
    LoginUsecase loginUsecase() {
        new LoginUsecase(userRepository, tokenProvider)
    }

    @Bean
    CreateUserUsecase createUserUsecase() {
        new CreateUserUsecase(userRepository)
    }
}
