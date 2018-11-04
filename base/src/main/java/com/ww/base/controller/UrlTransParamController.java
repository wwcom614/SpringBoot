package com.ww.base.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class UrlTransParamController {

    //URL路径方式传参：http://localhost:80/person/ww
    @GetMapping(value = "/{path_param}")
    public String pathParam(@PathVariable("path_param") String myName) {
        return new StringBuilder().append("pathParam：").append(myName).toString();
    }

    //URL参数方式传参：http://localhost:80/person/url_param?id=123
    @GetMapping("/url_param")
    public String findLikeDeptName(@RequestParam(value = "id",
            required = false,//不是必填
            defaultValue = "0") //默认值是0
                                           Integer myID) {
        return new StringBuilder().append("urlParam：").append(myID).toString();
    }


}
