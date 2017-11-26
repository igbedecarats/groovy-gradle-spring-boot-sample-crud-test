package org.test.springbootgroovytest.users.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ToStringBuilder

import javax.persistence.*

import static org.apache.commons.lang3.Validate.notNull

@Entity
@Table(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
class User {

    @Id
    @GeneratedValue
    long id

    @Column(name = "username", nullable = false, unique = true)
    String username

    @Column(name = "password", nullable = false)
    String password

    @Column(name = "first_name", nullable = false)
    String firstName

    @Column(name = "last_name", nullable = false)
    String lastName

    @Column(name = "email", nullable = false, unique = true)
    String email

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    UserRole role

    void setRole(final UserRole role) {
        notNull(role, "The UserRole cannot be null")
        this.role = role
    }

    def isAdmin() {
        return role.equals(UserRole.ADMIN)
    }

    def isUser() {
        return role.equals(UserRole.USER)
    }

    @Override
    boolean equals(final Object o) {
        if (this == o) {
            return true
        }

        if (o == null || getClass() != o.getClass()) {
            return false
        }

        User user = (User) o

        return new EqualsBuilder().append(id, user.id).append(username, user.getUsername())
                .append(email, user.getEmail()).isEquals()
    }

    @Override
    int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(username).append(email).toHashCode()
    }

    @Override
    String toString() {
        return new ToStringBuilder(this).append("id", id).append("username", username)
                .append("email", email).append("firstName", firstName)
                .append("lastName", lastName).toString()
    }
}
