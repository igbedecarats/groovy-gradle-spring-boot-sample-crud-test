package org.test.springbootgroovytest.users.domain

import javax.persistence.*

@Entity
@Table(name = "user")
class User {

    @Id
    @GeneratedValue
    long id;

    @Column(name = "username", nullable = false, unique = true)
    String username

    @Column(name = "first_name", nullable = false)
    String firstName

    @Column(name = "last_name", nullable = false)
    String lastName
}
