package org.test.springbootgroovytest.global.security

class Token {

    private String token
    private String username
    private Date expiration

    Token(final String token, final String username, final Date expiration) {
        this.token = token
        this.username = username
        this.expiration = expiration
    }

    String getToken() {
        return token
    }

    String getUsername() {
        return username
    }

    Date getExpiration() {
        return expiration
    }

    boolean hasExpired() {
        return System.currentTimeMillis() > this.expiration.getTime()
    }
}
