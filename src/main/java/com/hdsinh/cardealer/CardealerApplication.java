package com.hdsinh.cardealer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hdsinh.cardealer" )
public class CardealerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardealerApplication.class, args);
    }

}
