package com.example.learningtest.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcSumTest {
    Calculator calculator;
    String numFilePath;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        numFilePath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    void sumOfNumbers() throws IOException {
        int sum = calculator.calcSum(numFilePath);

        assertThat(sum).isEqualTo(10);
    }

    @Test
    void mulOfNumbers() throws IOException{
        int mul = calculator.calcMul(numFilePath);

        assertThat(mul).isEqualTo(24);
    }

    @Test
    void concatenateStrings() throws IOException {
        String concatenate = calculator.concatenate(numFilePath);

        assertThat(concatenate).isEqualTo("1234");
    }
}
