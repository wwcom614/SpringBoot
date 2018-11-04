package com.ww.base.service;

import com.ww.base.model.DeptModel;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DeptService {

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    List<DeptModel> findLikeDeptName(String deptName);

    @Transactional(propagation = Propagation.REQUIRED)
    int insert(DeptModel deptModel);
}
