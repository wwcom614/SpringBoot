package com.ww.base.service.impl;

import com.ww.base.model.DeptModel;
import com.ww.base.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;

/*    @Resource
    private RedisTemplate<String, Object> redisOpStrObj;*/

    @Test
    public void redisSetGetStr() throws Exception {
        Boolean succFlag = redisUtil.set("ww1","1234");
        log.info("【redisUtil.set success】：{}",succFlag);
        log.info("【redisUtil.get(key)】：{}",redisUtil.get("ww1"));
    }

    @Test
    public void redisSetGetObj() throws Exception{
        DeptModel deptModel = new DeptModel();
        deptModel.setDeptNo(101L);
        deptModel.setDeptName("架构部");
        Boolean succFlag = redisUtil.set("ww2",deptModel);
        log.info("【redisUtil.set success】：{}",succFlag);
        log.info("【redisUtil.get(key)】：{}",redisUtil.get("ww2"));
    }

}