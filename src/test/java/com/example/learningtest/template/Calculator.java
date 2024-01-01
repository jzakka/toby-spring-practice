package com.example.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
        return lineReadTemplate(filePath, (result, line) -> result + Integer.parseInt(line), 0);
    }

    public Integer calcMul(String filePath) throws IOException {
        return lineReadTemplate(filePath, (result, line) -> result * Integer.parseInt(line), 1);
    }

    public String concatenate(String filePath) throws IOException {
        return lineReadTemplate(filePath, (result, line) -> result + line, "");
    }

    private <T> T lineReadTemplate(String filepath, BiFunction<T, String, T> callback, T initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));

            T result = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                result = callback.apply(result, line);
            }
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
