package org.test.springbootgroovytest.users.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.test.springbootgroovytest.users.usecase.LoginRequest
import org.test.springbootgroovytest.users.usecase.LoginUsecase

@RestController
@RequestMapping('/login')
class LoginController {

    @Autowired
    private LoginUsecase loginUsecase

    @RequestMapping(method = RequestMethod.POST, produces = ['application/json'])
    ResponseEntity<?> addUser(@RequestBody final LoginRequest request) {
        ResponseEntity.ok(loginUsecase.login(request))
    }
}
