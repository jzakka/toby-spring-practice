package com.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(locations = "/junit.xml")
public class JUnitTest {
    @Autowired
    ApplicationContext context;
    static ApplicationContext contextObject = null;
    static Set<JUnitTest> testObject = new HashSet<>();

    @Test
    void test1() {
        assertThat(this).isNotIn(testObject);
        testObject.add(this);

        assertThat(contextObject == null || contextObject == this.context)
                .isTrue();
        contextObject = this.context;
    }

    @Test
    void test2() {
        assertThat(this).isNotIn(testObject);
        testObject.add(this);

        assertThat(contextObject == null || contextObject == this.context)
                .isTrue();
        contextObject = this.context;
    }

    @Test
    void test3() {
        assertThat(this).isNotIn(testObject);
        testObject.add(this);

        assertThat(contextObject == null || contextObject == this.context)
                .isTrue();
        contextObject = this.context;
    }
}
