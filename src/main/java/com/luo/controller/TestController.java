package com.luo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luo.entity.Clazz;
import com.luo.entity.Student;
import com.luo.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/hello")
public class TestController {

    @RequestMapping("/hello")
    @ResponseBody
    public Object hello() throws Exception {
        /*Student student = new Student();
        student.setName("郭亮");
        student.setPassword("123");
        student.setAge("23");
        student.setBirthday(new Date());

        ObjectMapper objectMapper =new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(student));*/
        /*ObjectMapper objectMapper =new ObjectMapper();
        Student student = new Student();
        String studentString = FileUtil.getFile("E:\\JavaFile\\SpringBoot\\Student.txt");
        student = objectMapper.readValue(studentString,Student.class);*/
        ObjectMapper objectMapper =new ObjectMapper();

        /*Student student = new Student();
        student.setName("郭亮");
        student.setPassword("123");
        student.setAge("23");
        student.setBirthday(new Date());

        Student student1 = new Student();
        student1.setName("周华");
        student1.setPassword("789");
        student1.setAge("24");
        student1.setBirthday(new Date());

        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        Clazz clazz = new Clazz();
        clazz.setName("高一5班");
        clazz.setList(list);*/
        //String clazzString = FileUtil.getFile("E:\\JavaFile\\SpringBoot\\ClassArray.txt");
        byte[] b = FileUtil.getByteFromFile("E:\\JavaFile\\SpringBoot\\ClassArray.txt");
        String clazzString = new String(b,"GBK");
        List<Clazz> list = new ArrayList<>();
        list = objectMapper.readValue(clazzString,new TypeReference<List<Clazz>>(){});
        String clazzS = objectMapper.writeValueAsString(list);
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("list",list);
        System.out.println(jsonObject.toString());

        System.out.println(objectMapper.writeValueAsString(list));
        return clazzS;
    }

    @RequestMapping("/helloThymeleaf")
    public String helloThymeleaf(HttpServletRequest request, Model model){
        Student student = new Student();
        student.setName("周华");
        student.setAge("27");
        student.setBirthday(new Date());
        Student student1 = new Student();
        student1.setName("周华1");
        student1.setAge("271");
        student1.setBirthday(new Date());
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student1);
        students.add(student);
        students.add(student1);
        students.add(student);
        students.add(student1);
        model.addAttribute("students",students);
        return "hello";
    }


}
