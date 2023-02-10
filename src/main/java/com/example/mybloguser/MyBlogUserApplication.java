package com.example.mybloguser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyBlogUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBlogUserApplication.class, args);
    }

}
