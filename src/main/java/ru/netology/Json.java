package ru.netology;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Json {
    public static String readString(String fileName) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                line = reader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }

    public static List<Employee> jsonToList(String json) {
        List<Employee> employee = new ArrayList<>();
        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
        GsonBuilder builder = new GsonBuilder();
        for (JsonElement jsonEl : jsonArray) {
            Employee employee1 = builder.create().fromJson(jsonEl, Employee.class);
            employee.add(employee1);
        }
        return employee;

    }
}
