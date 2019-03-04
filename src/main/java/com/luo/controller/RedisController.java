package com.luo.controller;

import com.luo.entity.Student;
import com.luo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * https://www.yiibai.com/redis
 */

@Controller
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/saveObject")
    @ResponseBody
    public String saveObject(){
        Student student = new Student();
        student.setId(1);
        student.setName("郭亮");
        for (int i=1;i<10;i++){
            redisUtil.saveObject("str"+i, "str"+i);
        }
        redisUtil.saveObject("stu", student);
        Map<String,String> map =new HashMap<>();
        map.put("name","郭亮");
        redisUtil.saveObject("map", map);
        redisUtil.saveMap("kk", map);
        return "";
    }

    @RequestMapping(value = "/getObject")
    @ResponseBody
    public Object getObject(String key){
        return  redisUtil.getVal(key);
    }

    @RequestMapping(value = "/getMap")
    @ResponseBody
    public Object getMap(String key){
        return  redisUtil.getMap(key);
    }

    @RequestMapping(value = "/deleteObject")
    @ResponseBody
    public Object deleteObject(String key){
        return  redisUtil.deleteObject(key);
    }

    @RequestMapping(value = "/rename")
    @ResponseBody
    public Object rename(String key,String oldKey){
        return  redisUtil.rename(key,oldKey);
    }

    @RequestMapping(value = "/expire")
    @ResponseBody
    public Object expire(String key,long time){
        return  redisUtil.expire(key,time);
    }

    @RequestMapping(value = "/multiGet")
    @ResponseBody
    public Object multiGet(){
        Collection<String> keys = new ArrayList<>();
        keys.add("str1");
        keys.add("str2");
        return  redisUtil.multiGet(keys);
    }

}
