package org.test.springbootgroovytest.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.test.springbootgroovytest.global.web.GlobalExceptionHandler
import org.test.springbootgroovytest.global.web.HttpApiError

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private GlobalExceptionHandler exceptionHandler

    private TokenProvider tokenProvider

    private ObjectMapper mapper = new ObjectMapper()

    TokenAuthenticationFilter(final GlobalExceptionHandler exceptionHandler,
                              TokenProvider tokenProvider) {
        super("/api/**")
        super.setRequiresAuthenticationRequestMatcher(
                new OrRequestMatcher(new AntPathRequestMatcher("/api/**")))
        this.exceptionHandler = exceptionHandler
        this.tokenProvider = tokenProvider
    }

    @Override
    Authentication attemptAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Authentication authentication = null
        try {
            Token token = getToken(request)
            AbstractAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    token.username, token.token)
            authentication = getAuthenticationManager().authenticate(userAuthenticationToken)
        } catch (final Exception exception) {
            ResponseEntity<HttpApiError> responseEntity = exceptionHandler
                    .toResponseEntity(exception, HttpStatus.UNAUTHORIZED)
            HttpApiError httpApiError = responseEntity.getBody()
            response.setStatus(httpApiError.getStatus().value())
            response.setHeader("Content-Type", "application/jsoncharset=UTF-8")
            response.getWriter().write(mapper.writeValueAsString(httpApiError))
        }
        return authentication
    }

    private Token getToken(final HttpServletRequest request) {
        String tokenPayload = request.getHeader("Authorization")
        validateTokenHeaderExists(tokenPayload)
        def body = tokenProvider.parseClaims(tokenPayload).getBody()
        Token token = new Token(tokenPayload, body.getSubject(), body.getExpiration())
        if (token.hasExpired()) {
            throw new TokenExpiredException("The token has expired")
        }
        request.setAttribute("TOKEN", token)
        return token
    }

    private void validateTokenHeaderExists(final String tokenPayload) {
        if (tokenPayload == null) {
            throw new TokenNotFoundException(
                    "No token found in the request scope. Maybe it is missing from headers.")
        }
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult)
        chain.doFilter(request, response)
    }
}
