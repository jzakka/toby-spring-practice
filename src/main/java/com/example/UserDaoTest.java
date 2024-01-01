package com.example;

import com.example.dao.UserDaoJdbc;
import com.example.domain.Level;
import com.example.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDaoJdbc userDao = context.getBean("userDao", UserDaoJdbc.class);

        User user = new User("jzakka", "정상화", "spring", Level.GOLD, 100, 40);
        user.setId("normalize");
        user.setName("정상화");
        user.setPassword("passwd");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getId() + " 조회 성공");

        if (!user.getName().equals(user2.getName())) {
            System.out.println("테스트 실패 (name)");
        } else if (!user.getPassword().equals(user2.getPassword())) {
            System.out.println("테스트 실패 (password)");
        } else {
            System.out.println("조회 테스트 성공");
        }
    }
}
