package com.company;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketingOrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingOrmApplication.class, args);
    }



    @Bean // To be able to use org.modelmapper classes methods.
    public ModelMapper mapper(){
        return new ModelMapper();
    }

}
