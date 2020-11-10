package com.klitwynenko.shoutbox;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

@SpringBootApplication
public class ShoutboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoutboxApplication.class, args);
    }

    @Bean
    public ApplicationRunner initializer(MessageRepository repository) {
        if(repository.findAll().iterator().hasNext())
            return args -> {};

        return args -> repository.saveAll(
                Arrays.asList(
                    new Message("User1", new Date(), "Example message"),
                    new Message("User1", new Date(), "Hello!"),
                    new Message("User2", new Date(), "Hello! Hello! Hello!")
                )
        );
    }
}
