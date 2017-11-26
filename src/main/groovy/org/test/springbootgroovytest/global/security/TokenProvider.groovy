package org.test.springbootgroovytest.global.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm

import java.time.LocalDateTime
import java.time.ZoneId

class TokenProvider {

    private String secretKey

    TokenProvider(final String secretKey) {
        this.secretKey = secretKey
    }

    def provide(final String subject) {
        Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact()
    }

    def parseClaims(final String token) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
    }
}
