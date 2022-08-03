package com.example.readLargeFile;

import com.example.readLargeFile.service.DataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException {
        var context = SpringApplication.run(Main.class, args);
        var service = (DataService) context.getBean("dataService");
        service.readData();
    }
}
