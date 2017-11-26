package org.test.springbootgroovytest.global.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.test.springbootgroovytest.users.domain.User
import org.test.springbootgroovytest.users.domain.UserRepository

class UserAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository

    UserAuthenticationProvider(final UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @Override
    Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) authentication
        final String username = (String) usernamePasswordAuthenticationToken.getPrincipal()
        User user = userRepository.findByUsername(username)
        if (user != null) {
            AbstractAuthenticationToken abstractAuthenticationToken =
                    getAbstractAuthenticationToken(user)
            abstractAuthenticationToken.setAuthenticated(true)
            return abstractAuthenticationToken
        }
        throw new UsernameNotFoundException(username)
    }

    private AbstractAuthenticationToken getAbstractAuthenticationToken(User user) {
        return new AbstractAuthenticationToken(Arrays.asList(user.getRole())) {
            @Override
            Object getCredentials() {
                return user.getPassword()
            }

            @Override
            Object getPrincipal() {
                return user
            }
        }
    }

    @Override
    boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)
    }
}
