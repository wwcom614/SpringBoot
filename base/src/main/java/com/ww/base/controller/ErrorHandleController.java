package com.ww.base.controller;

import com.ww.base.util.AbstractBaseController;
import com.ww.base.vo.PersonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Iterator;

@Slf4j
@Controller
@RequestMapping("/person")
//  http://localhost:80/person/pre_add
public class ErrorHandleController extends AbstractBaseController {
    @GetMapping("/pre_add")
    public String preAddPerson() {
        System.out.println(1/0);
        return "person_add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Object addPerson(@Valid PersonVo personVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Iterator<ObjectError> iterator = bindingResult.getAllErrors().iterator();
            while (iterator.hasNext()) {
                ObjectError objectError = iterator.next();
                log.error("【输入错误】 code = {}, message = {}" ,objectError.getCode() ,objectError.getDefaultMessage());
            }
            return bindingResult.getAllErrors();
        } else {
            return personVo;
        }
    }


}
