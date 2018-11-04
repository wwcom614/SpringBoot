package com.ww.base.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {

    @Resource
    private DataSource dataSource;

    @Test
    public void testDBConnection() throws Exception {
        log.info(String.valueOf(dataSource.getConnection()));
    }



}