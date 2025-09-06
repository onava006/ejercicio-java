package com.smartjob.ejerciciojava.application.configuration;


import com.smartjob.ejerciciojava.domain.adapters.input.PhoneServiceImpl;
import com.smartjob.ejerciciojava.domain.adapters.input.UserServiceImpl;
import com.smartjob.ejerciciojava.domain.ports.input.PhoneService;
import com.smartjob.ejerciciojava.domain.ports.input.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {


    @Bean
    UserService getUserService(){
        return new UserServiceImpl();
    }

    @Bean
    PhoneService getPhoneService(){
        return new PhoneServiceImpl();
    }

}
