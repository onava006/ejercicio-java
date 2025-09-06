package com.smartjob.ejerciciojava.application.utils;

import com.smartjob.ejerciciojava.domain.exception.InvalidEmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserEmailValidator {

    @Value("${com.smartjob.ejerciciojava.email.validation.regular-expression}")
    String emailRegex;

    @Value("${com.smartjob.ejerciciojava.exception.error002}")
    String invalidEmailError;

    public  boolean validateEmail(String emailStr) throws InvalidEmailException {

        Pattern emailRegexPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegexPattern.matcher(emailStr);
        boolean matchesPattern = matcher.matches();
        if(!matchesPattern) throw new InvalidEmailException(invalidEmailError);
        return matcher.matches();
    }
}
