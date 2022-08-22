package com.example.readLargeFile.parser;

import com.example.readLargeFile.model.Information;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityParser {

    private static final ArrayList<String> buffArray = new ArrayList<>();
    private static final StringBuilder buffWord = new StringBuilder();

    public static ArrayList<Information> parsing(ArrayList<String> texts) {
        var dats = new ArrayList<Information>();
        var iterator = texts.stream().iterator();

        while (iterator.hasNext()){
            fillBuffArray(iterator.next());

            var name = buffArray.get(0);
            var text = buffArray.get(1);
            var number = new BigInteger(buffArray.get(2));
            var uuidList = fillArrayUUID();
            var l = uuidList.size();
            var i = l == 0 ? 1 : l;
            var count = 4 + i;
            var stringList = fillArrayText(count);

            var id = new BigInteger(buffArray.get(buffArray.size() - 1));
            buffArray.clear();
            dats.add(new Information(id, name, text, number, uuidList, stringList));
        }

        return dats;
    }

    private static void fillBuffArray(String fields) {
        for (var i = 0; i < fields.length(); i++) {
            if (fields.charAt(i) == '\"')
                continue;

            if (fields.charAt(i) == ',' || fields.charAt(i) == '\n') {
                buffArray.add(buffWord.toString());
                buffWord.setLength(0);
                continue;
            }

            buffWord.append(fields.charAt(i));
        }
    }

    private static List<UUID> fillArrayUUID() {
        var numberList = new ArrayList<UUID>();
        if (buffArray.get(3).equals("{"))
            return numberList;

        numberList.add(UUID.fromString(buffArray.get(3).substring(1)));

        if (buffArray.get(4).equals("}"))
            return numberList;

        for (var i = 4; i < buffArray.size(); i++) {
            if (buffArray.get(i).indexOf('}') != -1) {
                numberList.add(UUID.fromString(buffArray.get(i).substring(0, buffArray.get(i).length() - 1)));
                break;
            }
            numberList.add(UUID.fromString(buffArray.get(i)));
        }

        return numberList;
    }

    private static List<String> fillArrayText(int count) {
        var textList = new ArrayList<String>();

        if (buffArray.get(3).equals("{"))
            return textList;
        textList.add(buffArray.get(count).substring(1));

        if (buffArray.get(4).equals("}"))
            return textList;

        for (var i = count + 1; i < buffArray.size(); i++) {
            if (buffArray.get(i).indexOf('}') != -1) {
                textList.add(buffArray.get(i).substring(0, buffArray.get(i).length() - 1));
                break;
            }
            textList.add(buffArray.get(i));
        }

        return textList;
    }
}
