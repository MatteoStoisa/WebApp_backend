package it.polito.ai.laboratorio2;

import it.polito.ai.laboratorio2.services.TeamService;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

    @Bean
    CommandLineRunner runner(@Autowired TeamService teamService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                try {
                    //DEBUG HERE!

                    /* //csv debug
                    ClassPathResource classPathResource = new ClassPathResource("static\\upload_example.csv");
                    Reader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
                    for (Boolean bool : teamService.addAndEroll(reader, "Analisi 1") )
                        log.info(bool.toString());
                     */

                } catch (Exception exception) {
                    log.warning("OH NO! |*.*| Exception occurred -> " + exception.toString());
                }
            }
        };
    }


}
