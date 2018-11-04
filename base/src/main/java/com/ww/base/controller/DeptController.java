package com.ww.base.controller;

import com.ww.base.model.DeptModel;
import com.ww.base.service.DeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    //URL参数方式传参：http://localhost:80/dept/find_like_deptname?dept_name=开发部
    @GetMapping("/find_like_deptname")
    public String find_like_deptname(@RequestParam(value = "dept_name",
            required = true,//必填
            defaultValue = "") //默认值是空
                                   String deptName) {
        List<DeptModel> deptModels = deptService.findLikeDeptName(deptName);
        return new StringBuilder().append("DEPT：").append(deptModels).toString();
    }
}
