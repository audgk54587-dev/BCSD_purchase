package com.group.purchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BcsdPurchaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BcsdPurchaseApplication.class, args);
    }

}
