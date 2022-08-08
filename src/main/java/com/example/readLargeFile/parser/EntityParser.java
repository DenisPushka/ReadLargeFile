package com.example.readLargeFile.parser;

import com.example.readLargeFile.model.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.UUID;

public class EntityParser {

    private static final ArrayList<String> buffArray = new ArrayList<>();
    private static final StringBuilder buffWord = new StringBuilder();

    public static ArrayList<Data> parsing(ArrayList<String> texts) {
        var dats = new ArrayList<Data>();
        var iterator = texts.stream().iterator();

        while (iterator.hasNext()){
            fillBuffArray(iterator.next());

            var name = buffArray.get(0);
            var text = buffArray.get(1);
            var number = new BigInteger(buffArray.get(2));
            var arrayUUID = fillArrayUUID();
            var l = arrayUUID.length;
            var i = l == 0 ? 1 : l;
            var count = 4 + i;
            var arrayText = fillArrayText(count);

            var id = new BigInteger(buffArray.get(buffArray.size() - 1));
            buffArray.clear();
            dats.add(new Data(id, name, text, number, arrayUUID, arrayText));
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

    private static UUID[] fillArrayUUID() {
        var numberList = new ArrayList<UUID>();
        if (buffArray.get(3).equals("{"))
            return numberList.toArray(new UUID[0]);

        numberList.add(UUID.fromString(buffArray.get(3).substring(1)));

        if (buffArray.get(4).equals("}"))
            return numberList.toArray(new UUID[0]);

        for (var i = 4; i < buffArray.size(); i++) {
            if (buffArray.get(i).indexOf('}') != -1) {
                numberList.add(UUID.fromString(buffArray.get(i).substring(0, buffArray.get(i).length() - 1)));
                break;
            }
            numberList.add(UUID.fromString(buffArray.get(i)));
        }

        return numberList.toArray(UUID[]::new);
    }

    private static String[] fillArrayText(int count) {
        var textList = new ArrayList<String>();

        if (buffArray.get(3).equals("{"))
            return textList.toArray(new String[0]);
        textList.add(buffArray.get(count).substring(1));

        if (buffArray.get(4).equals("}"))
            return textList.toArray(new String[0]);

        for (var i = count + 1; i < buffArray.size(); i++) {
            if (buffArray.get(i).indexOf('}') != -1) {
                textList.add(buffArray.get(i).substring(0, buffArray.get(i).length() - 1));
                break;
            }
            textList.add(buffArray.get(i));
        }

        return textList.toArray(new String[0]);
    }
}
