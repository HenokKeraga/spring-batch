package com.example.springbatchultimate;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchUltimateApplication {

    public static void main(String[] args) {

        List<String> strings = Arrays.asList(args);

        List<String> finalArgs = new ArrayList<>(strings.size() + 1);
        finalArgs.addAll(strings);
        finalArgs.add("inputFlatFile=/data/customer.csv");


        SpringApplication.run(SpringBatchUltimateApplication.class, finalArgs.toArray(new String[finalArgs.size()]));
    }

}
