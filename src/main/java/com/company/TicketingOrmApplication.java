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



    /*
     * · Spring always accepts encoded password
     * · BCryptPasswordEncoder class is implemented from PasswordEncoder Interface
     *
     */
    @Bean // To override the spring encoded password
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
