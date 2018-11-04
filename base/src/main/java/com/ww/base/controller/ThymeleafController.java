package com.ww.base.controller;

import com.ww.base.vo.PersonVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


//访问静态页面：http://localhost/index.html

@Controller
@RequestMapping("/person")
public class ThymeleafController {



    //从URL中获取参数，使用Model传给模板thymeleaf_person渲染：http://localhost:80/person/th?name=ww&&age=38
    @GetMapping("/th")
    public String thymeleaf_person(@RequestParam(value="name") String name,
                                   @RequestParam(value="age",required = false,defaultValue = "20") Integer age,
                                   Model model,
                                   HttpServletRequest httpServletRequest){
        model.addAttribute("person_name",name);

        //不推荐使用，只是为了测试thymeleaf的utext可以不转义，得到原生html
        model.addAttribute("person_age","<span style='color:blue'>"+ age + "</span>");

        //Model传递的本质就是属于request属性范围，所以HttpServletRequest也可以通过HTTP内置对象将信息传递给Thymeleaf。
        httpServletRequest.setAttribute("request_th", "http_req_info");
        httpServletRequest.getSession().setAttribute("session_th", "http_session_info");
        httpServletRequest.getServletContext().setAttribute("application_th", "http_application_info");

        //对象输出到Thymeleaf
        PersonVo personVo = new PersonVo();
        personVo.setId(101L);
        personVo.setName("ww");
        personVo.setAge(38);
        personVo.setSalary(54321.98);
        personVo.setEmail("wwcom614@qq.com");
        personVo.setBirthday(new Date());
        model.addAttribute("person_ww", personVo);

        //List对象输出到Thymeleaf
        List<PersonVo> personVoList = new ArrayList<PersonVo>();
        for(int i=0; i<10; i++){
            PersonVo personVoTmp = new PersonVo();
            personVoTmp.setId(101L + i);
            personVoTmp.setName("ww" + i);
            personVoTmp.setAge(10 + i);
            personVoTmp.setSalary(54321.98+ i*1000);
            personVoTmp.setEmail("wwcom614@qq.com" + i);
            personVoTmp.setBirthday(new Date());
            personVoList.add(personVoTmp);
        }
        model.addAttribute("person_list",personVoList);

        //Map对象输出到Thymeleaf
        Map<Integer,PersonVo> personVoMap = new HashMap<Integer, PersonVo>();
        for(int i=1; i<=10; i++){
            PersonVo personVoMapTmp = new PersonVo();
            personVoMapTmp.setId(101L + i);
            personVoMapTmp.setName("ww" + i);
            personVoMapTmp.setAge(10 + i);
            personVoMapTmp.setSalary(54321.98+ i*1000);
            personVoMapTmp.setEmail("wwcom614@qq.com" + i);
            personVoMapTmp.setBirthday(new Date());
            personVoMap.put(i,personVoMapTmp);
        }
        model.addAttribute("person_map",personVoMap);

        return "thymeleaf_person";
    }
}
