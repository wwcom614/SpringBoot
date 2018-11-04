package com.ww.base.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
public class PersonVo implements Serializable {

    private Long id;

    @Length(min = 1,message = "{PersonVo.name.length.error}")
    private String name;

    @NotNull(message = "{PersonVo.age.notnull.error}")
    @Digits(integer = 3,fraction = 0,message = "{PersonVo.age.digits.error}")
    private Integer age;

    @Length(min = 6, message = "{PersonVo.email.length.error}")
    @Email(message = "{PersonVo.email.email.error}")
    private String email;

    @NotNull(message = "{PersonVo.salary.notnull.error}")
    @Min(value = 1,message = "{PersonVo.salary.min.error}")
    private Double salary;

    @NotNull(message = "{PersonVo.birthday.notnull.error}")
    //@Future(message = "{PersonVo.birthday.future.error}")
    private Date birthday;
}
