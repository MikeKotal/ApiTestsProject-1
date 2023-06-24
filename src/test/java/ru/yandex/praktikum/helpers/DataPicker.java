package ru.yandex.praktikum.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DataPicker {

    private static final String startPath = "src/test/resources/testDates/";

    public static JsonElement getData(String endPath, String partition) {
        String path = startPath + endPath + ".json";
        JsonElement getData = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8))) {
            if (partition == null) {
                getData = new Gson().fromJson(br, JsonElement.class);
            } else {
                getData = new Gson().fromJson(br, JsonElement.class).getAsJsonObject().get(partition);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getData;
    }

}
