package org.fiuba.sii.springbootgroovytest.users.domain

import org.springframework.data.repository.CrudRepository

interface UserRepository extends CrudRepository<User, Long> {
}
