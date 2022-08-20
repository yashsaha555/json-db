package com.sourav.JSONdb;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sourav.JSONdb.domain.User;
import com.sourav.JSONdb.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class JsoNdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsoNdbApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService) {
        return args -> {
            //read json and write to db
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<User>> typeReference = new TypeReference<>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
            try {
                List<User> users = objectMapper.readValue(inputStream, typeReference);
                userService.save(users);
                System.out.println("Users saved");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Unable to save user");
            }
        };
    }
}

