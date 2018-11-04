package com.ww.base.mapper;

import com.ww.base.model.DeptModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptMapper {
    //XML方式
    List<DeptModel> findAll();
    DeptModel findOneByDeptNo(Integer deptNo);

    //注解方式
    //Insert的字段Map方式
    @Insert("insert into dept(dept_name) values (#{dept_name,jdbcType=VARCHAR})")
    int insertByMap(Map<String,Object> map);

    //Insert的Object方式，更常用
    @Insert("insert into dept(dept_name) values (#{deptName,jdbcType=VARCHAR})")
    int insertByObject(DeptModel deptModel);

    //尝试下like。实际生产环境中，like肯定不能两边%这么用
    @Select("select dept_no, dept_name from dept where dept_name like concat('%',#{dept_name,jdbcType=VARCHAR},'%')")
    @Results({
            @Result(column = "dept_no", property = "deptNo"),
            @Result(column = "dept_name", property = "deptName")
    })
    List<DeptModel> findLikeDeptName(String deptName);

    //Update的字段方式
    @Update("update dept set dept_name = #{dept_name} where dept_no = #{dept_no}")
    int updateByDeptNo(@Param("dept_no") Integer deptNo,
                       @Param("dept_name") String deptName);

    //Update的Object方式，不能以自增主键为where条件
    @Update("update dept set dept_name = #{dept_name} where dept_no = #{dept_no}")
    int updateByObject(DeptModel deptModel);

    @Delete("delete from dept where dept_no = #{dept_no}")
    int deleteByDeptNo(Integer deptNo);
}
