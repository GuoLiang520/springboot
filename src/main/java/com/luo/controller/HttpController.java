package com.luo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "ab")
public class HttpController {

    @RequestMapping(value = "a",method = RequestMethod.PATCH,produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getPost(@RequestBody String parm, HttpServletResponse response, HttpServletRequest request){
        String test = request.getHeader("test");
        System.out.println(test);
        System.out.println(parm);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "周华";
    }
}
