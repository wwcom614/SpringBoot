package com.ww.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class GetHttpParamController {
    //获取请求和响应的内置对象：http://localhost:80/innerobj
    @RequestMapping("/innerobj")
    public String getInnerObj(HttpServletRequest request, HttpServletResponse response) {
        String clientIP = request.getRemoteAddr();
        String respCharactorEncoding = response.getCharacterEncoding();
        String sessionID = request.getSession().getId();
        String realPath = request.getServletContext().getRealPath("/config-class");

        return new StringBuilder().append("clientIP：").append(clientIP).append("\r\nrespCharactorEncoding：").append(respCharactorEncoding).append("\r\nsessionID：").append(sessionID).append("\r\nrealPath：").append(realPath).toString();
    }
}
