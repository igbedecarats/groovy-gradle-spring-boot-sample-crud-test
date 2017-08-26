package org.fiuba.sii.springbootgroovytest.users.web

import org.fiuba.sii.springbootgroovytest.users.domain.User
import org.fiuba.sii.springbootgroovytest.users.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/users')
class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, produces = ['application/json'])
    ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll())
    }
}
