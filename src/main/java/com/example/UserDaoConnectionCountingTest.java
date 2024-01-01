package com.example;

import com.example.dao.CountingConnectionMaker;
import com.example.dao.DaoFactory;
import com.example.dao.UserDaoJdbc;
import com.example.domain.Level;
import com.example.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoJdbc userDao = context.getBean("userDao", UserDaoJdbc.class);

        User user = new User("jzakka", "정상화", "spring", Level.GOLD, 100, 40);
        user.setId("normalize");
        user.setName("정상화");
        user.setPassword("passwd");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter:" + ccm.getCounter());
    }
}
