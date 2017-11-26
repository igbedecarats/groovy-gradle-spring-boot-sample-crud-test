package org.test.springbootgroovytest.users.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.test.springbootgroovytest.users.domain.UserRepository
import org.test.springbootgroovytest.users.usecase.CreateUserRequest
import org.test.springbootgroovytest.users.usecase.CreateUserUsecase

import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping('/api/users')
class UserController {

    @Autowired
    UserRepository userRepository

    @Autowired
    CreateUserUsecase createUserUsecase

    @RequestMapping(method = RequestMethod.GET, produces = ['application/json'])
    ResponseEntity<?> getUsers() {
        ResponseEntity.ok(userRepository.findAll())
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET, produces = ['application/json'])
    ResponseEntity<?> getUser(@PathVariable final long userId) {
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException()
        }
        ResponseEntity.ok(user)
    }

    @RequestMapping(method = RequestMethod.POST, produces = ['application/json'])
    ResponseEntity<?> addUser(@RequestBody final CreateUserRequest request) {
        new ResponseEntity<>(createUserUsecase.create(request), HttpStatus.CREATED)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE, produces = ['application/json'])
    ResponseEntity<?> deleteUser(@PathVariable final long userId) {
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException()
        }
        userRepository.delete(user.id)
        ResponseEntity.ok().build()
    }
}
