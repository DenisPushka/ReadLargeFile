package com.example.readLargeFile;

import com.example.readLargeFile.connect.Connect;
import com.example.readLargeFile.service.DataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        var context = SpringApplication.run(Main.class, args);
        var service = (DataService) context.getBean("dataService");
        Connect.connect();
        service.readData();
    }
}
