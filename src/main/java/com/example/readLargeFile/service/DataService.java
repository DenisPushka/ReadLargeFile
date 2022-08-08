package com.example.readLargeFile.service;

import com.example.readLargeFile.parser.EntityParser;
import com.example.readLargeFile.repository.DataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class DataService {
    private final DataRepository dataRepository;


    private static final String path = "D:\\UsersOutput.CSV";

    public void readData() {

        try (FileInputStream inputStream = new FileInputStream(path); Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8)) {

            var buff = new ArrayList<String>();
            var nameFields = false;

            while (sc.hasNextLine()) {
                if (!nameFields) {
                    nameFields = true;
                    sc.nextLine();
                }

                if (buff.size() > 10)
                    writeData(buff);

                String line = sc.nextLine();
                line += "\n";
                buff.add(line);
            }

            if (sc.ioException() != null) throw sc.ioException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeData(ArrayList<String> texts) {
        var dats = EntityParser.parsing(texts);
        dataRepository.save(dats.get(0));
        var a = dataRepository.findAll();
        var c = 0;
    }
}
