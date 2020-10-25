package com.sda;

import com.sda.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 */
@SpringBootApplication
public class SpringBootDemo implements CommandLineRunner {
    private final UserService userService;

    public SpringBootDemo(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
