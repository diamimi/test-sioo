package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class OpenApplication  {
    private static Logger LOGGER = LoggerFactory.getLogger(OpenApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(OpenApplication.class, args);
    }


}
