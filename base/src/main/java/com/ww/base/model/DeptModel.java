package com.ww.base.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
public class DeptModel implements Serializable {
    private Long deptNo;
    private String deptName;
}
