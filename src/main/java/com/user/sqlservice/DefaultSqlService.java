package com.user.sqlservice;

import com.user.sqlservice.BaseSqlService;
import com.user.sqlservice.HashMapSqlRegistry;
import com.user.sqlservice.JaxbXmlSqlReader;

public class DefaultSqlService extends BaseSqlService {
    public DefaultSqlService() {
        setSqlReader(new JaxbXmlSqlReader());
        setSqlRegistry(new HashMapSqlRegistry());
    }
}
