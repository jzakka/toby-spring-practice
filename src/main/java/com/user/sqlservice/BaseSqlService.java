package com.user.sqlservice;

import jakarta.annotation.PostConstruct;

public class BaseSqlService implements SqlService {
    protected SqlReader sqlReader;
    protected SqlRegistry sqlRegistry;

    @PostConstruct
    public void loadSql() {
        sqlReader.read(sqlRegistry);
    }
    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        try {
            return sqlRegistry.findSql(key);
        } catch (SqlNotFoundException e) {
            throw new SqlRetrievalFailureException(e);
        }
    }

    public void setSqlReader(SqlReader sqlReader) {
        this.sqlReader = sqlReader;
    }

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }
}
