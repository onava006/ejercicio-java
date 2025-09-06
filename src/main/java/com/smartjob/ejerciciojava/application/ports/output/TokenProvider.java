package com.smartjob.ejerciciojava.application.ports.output;

public interface TokenGenerator {

    public String createJwt(String subject, String issuer, long expirationMillis);
}
