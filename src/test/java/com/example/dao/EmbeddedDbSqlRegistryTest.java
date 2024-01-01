package com.example.dao;

import com.user.sqlservice.EmbeddedDbSqlRegistry;
import com.user.sqlservice.SqlUpdateFailureException;
import com.user.sqlservice.UpdatableSqlRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.*;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    EmbeddedDatabase db;
    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        db = new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .addScript("classpath:schema.sql")
                .build();

        EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlRegistry.setDataSource(db);

        return embeddedDbSqlRegistry;
    }

    @AfterEach
    void tearDown() {
        db.shutdown();
    }

    @Test
    void transactionalUpdate() {
        checkFindResult("SQL1", "SQL2", "SQL3");

        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY999!@#*&(", "Modified999");

        try {
            sqlRegistry.updateSql(sqlmap);
            fail();
        }catch (SqlUpdateFailureException e){}

        checkFindResult("SQL1", "SQL2", "SQL3");
    }
}
