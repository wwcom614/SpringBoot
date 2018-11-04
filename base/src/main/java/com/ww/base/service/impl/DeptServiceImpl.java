package com.ww.base.service.impl;


import com.ww.base.mapper.DeptMapper;
import com.ww.base.model.DeptModel;
import com.ww.base.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService{

    @Resource
    DeptMapper deptMapper;

    @Override
    public List<DeptModel> findLikeDeptName(String deptName) {
        return deptMapper.findLikeDeptName(deptName);
    }

    @Override
    public int insert(DeptModel deptModel) {
        return deptMapper.insertByObject(deptModel);
    }
}
