package com.example.learningtest.lifecycle;

import com.example.learningtest.lifecycle.bean.Human;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration("/lifeCycle.xml")
public class BeanLifeCycleTest {
    @Autowired
    Human human;

    @Test
    void postConstructTest() {
        Assertions.assertThat(human.getName()).isEqualTo("Chung");
    }
}
