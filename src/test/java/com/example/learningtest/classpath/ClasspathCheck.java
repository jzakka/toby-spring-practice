package com.example.learningtest.classpath;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClasspathCheck {
    @Test
    void checkClassPath() {
        for (String classpath : System.getProperty("java.class.path").split(":")) {
            System.out.println(classpath);
        }
    }

    @Test
    void classPathFind() throws IOException {
        ClassPathResource resource = new ClassPathResource("com/example/dao/sqlmap.xml");
        System.out.println(resource.getFilename());
        Assertions.assertThat(resource.isReadable()).isTrue();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
}
