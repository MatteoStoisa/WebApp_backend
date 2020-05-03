package it.polito.ai.laboratorio2;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Log(topic = "Laboratorio2Application")
public class Laboratorio2Application {

    public static void main(String[] args) {
        SpringApplication.run(Laboratorio2Application.class, args);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}


