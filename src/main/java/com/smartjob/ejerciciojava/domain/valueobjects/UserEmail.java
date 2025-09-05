package com.smartjob.ejerciciojava.domain.valueobjects;

import com.smartjob.ejerciciojava.domain.exception.InvalidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserEmail {

    static boolean validateEmail(String emailStr, String regex) throws InvalidEmailException {

        Pattern emailRegexPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegexPattern.matcher(emailStr);
        return matcher.matches();
    }
}
