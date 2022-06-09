package com.sparta.mini6_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Mini6BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Mini6BackendApplication.class, args);
    }

}
