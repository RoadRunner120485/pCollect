package de.sturmm.pcollect.application;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by sturmm on 20.09.15.
 */
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceConfig.class, args);
    }

}
