package com.company;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TicketingOrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingOrmApplication.class, args);
    }



    @Bean // To be able to use org.modelMapper classes methods.
    public ModelMapper mapper(){
        return new ModelMapper();
    }


    @Bean // To override the spring encoded password we use PasswordEncoder implementation class
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
