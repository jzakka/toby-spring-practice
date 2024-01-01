package com.example.dao;

import com.example.TestApplicationContext;
import com.example.domain.Level;
import com.example.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = TestApplicationContext.class)
//@DirtiesContext // 테스트 메서드에서 애플리케이션 컨텍스트 구성, 설정을 변경함을 테스트 컨텍스트 프레임워크에 알림
/*
테스트 컨텍스트는 DirtiesContext가 붙은 테스트 클래스는 애플리케이션 컨텍스트를 공유하지 않고 매 테스트마다 새로 만든다.
 */
public class UserDaoTest {
    @Autowired
    UserDaoJdbc userDao;
    @Autowired
    DataSource dataSource;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
//        userDao = new UserDaoJdbc();
//        DataSource dataSource = new SingleConnectionDataSource("jdbc:mariadb://localhost:3306/users", "lldj", "lldj123414", true);
        userDao.setDataSource(dataSource);

        user1 = new User("ema", "dustcell", "301", Level.BASIC, 1, 0);
        user2 = new User("ai", "higuchi", "132412", Level.SILVER, 55, 10);
        user3 = new User("jzakka", "정상화", "spring", Level.GOLD, 100, 40);
    }

    @Test
    void sqlMapSourceCheck() throws IOException {
        Resource resource = new ClassPathResource("sqlmap.xml", UserDao.class);
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String line;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Test
    void addAndGet() {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User userget1 = userDao.get(user1.getId());
        assertThat(user1.getName()).isEqualTo(userget1.getName());
        assertThat(user1.getPassword()).isEqualTo(userget1.getPassword());

        User userget2 = userDao.get(user2.getId());
        assertThat(user2.getName()).isEqualTo(userget2.getName());
        assertThat(user2.getPassword()).isEqualTo(userget2.getPassword());
    }

    @Test
    void getUserFailure() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        assertThatThrownBy(() -> userDao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void count() {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    void getAll() throws SQLException {
        userDao.deleteAll();

        List<User> users0 = userDao.getAll();
        assertThat(users0).isEmpty();

        userDao.add(user1);
        List<User> users1 = userDao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        userDao.add(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(1));
        checkSameUser(user2, users2.get(0));

        userDao.add(user3);
        List<User> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(0));
        checkSameUser(user3, users3.get(2));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }

    @Test
    void duplicateKey() {
        userDao.deleteAll();

        userDao.add(user1);
        assertThatThrownBy(()->userDao.add(user1))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void sqlExceptionTranslate() {
        userDao.deleteAll();

        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException) ex.getRootCause();
            SQLExceptionTranslator set =
                    new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assertThat(set.translate(null, null, sqlEx))
                    .isInstanceOf(DuplicateKeyException.class);
        }
    }

    @Test
    void update() {
        userDao.deleteAll();

        userDao.add(user1);
        userDao.add(user2);

        user1.setName("정나오");
        user1.setPassword("asdasd11");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        userDao.update(user1);

        User user1update = userDao.get(user1.getId());
        checkSameUser(user1, user1update);
        User user2same = userDao.get(user2.getId());
        checkSameUser(user2, user2same);
    }
}
