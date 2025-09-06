package com.smartjob.ejerciciojava.application.ports.output;

public interface TokenProvider {

    public String createJwt(String subject, String issuer, long expirationMillis);
}
