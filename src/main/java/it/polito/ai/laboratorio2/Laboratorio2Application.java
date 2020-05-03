package it.polito.ai.laboratorio2;

import it.polito.ai.laboratorio2.entities.User;
import it.polito.ai.laboratorio2.repositories.UserRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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

    @Component
    //@Slf4j
    public class DataInitializer implements CommandLineRunner {
        //...
        @Autowired
        UserRepository users;
        @Bean
        PasswordEncoder getEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Autowired
        PasswordEncoder passwordEncoder;
        @Override
        public void run(String... args) throws Exception {
            //...
            this.users.save(User.builder()
                    .username("user")
                    .password(this.passwordEncoder.encode("password"))
                    .roles(Arrays.asList( "ROLE_USER"))
                    .build()
            );
            this.users.save(User.builder()
                    .username("admin")
                    .password(this.passwordEncoder.encode("password"))
                    .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                    .build()
            );
            //log.debug("printing all users...");
            //this.users.findAll().forEach(v -> log.debug(" User :" + v.toString()));
        }
    }

}


