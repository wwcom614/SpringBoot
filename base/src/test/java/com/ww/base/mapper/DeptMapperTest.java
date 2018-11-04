package com.ww.base.mapper;

import com.ww.base.model.DeptModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptMapperTest {

    @Resource
    private DeptMapper deptMapper;

    @Test
    public void findAll() throws Exception {
        List<DeptModel> deptModels = deptMapper.findAll();
        log.info(String.valueOf(deptModels));
        Assert.assertNotEquals(0, deptModels.size());
    }

    @Test
    public void findOneByDeptNo() throws Exception {
        DeptModel result = deptMapper.findOneByDeptNo(2);
        log.info(String.valueOf(result));
        Assert.assertNotNull(result);
    }

    @Test
    public void insertByMap() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("dept_name","insertByMap");
        int result = deptMapper.insertByMap(map);
            Assert.assertEquals(1, result);
    }

    @Test
    public void insertByObject() throws Exception {
        DeptModel deptModel = new DeptModel();
        deptModel.setDeptName("insertByObject");
        int result = deptMapper.insertByObject(deptModel);
        Assert.assertEquals(1, result);
    }

    @Test
    public void findLikeDeptName() throws Exception {
        List<DeptModel> result = deptMapper.findLikeDeptName("关");
        log.info(String.valueOf(result));
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void updateByDeptNo() throws Exception {
        int result = deptMapper.updateByDeptNo(6,"updateByDeptNo");
        Assert.assertEquals(1,result);
    }

    @Test
    public void updateByObject() throws Exception {
        DeptModel deptModel = new DeptModel();
        deptModel.setDeptNo(7L);
        deptModel.setDeptName("updateByObject");
        int result = deptMapper.insertByObject(deptModel);
        Assert.assertEquals(1, result);
    }

    @Test
    public void deleteByDeptNo() throws Exception {
        int result = deptMapper.deleteByDeptNo(9);
        Assert.assertEquals(1, result);
    }
}