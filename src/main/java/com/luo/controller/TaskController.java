package com.luo.controller;


import com.luo.service.Office;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RootLogger;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@RequestMapping(value ="/task")
public class TaskController {

    @RequestMapping(value = "/start")
    @ResponseBody
    public String startOffice(HttpServletRequest request){
        String classPath = request.getParameter("path");
        try {
            Class c = Class.forName(classPath);
            Office office = (Office) c.newInstance();
            office.start();
            return "succ";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "err";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "err";
        } catch (InstantiationException e) {
            e.printStackTrace();
            return "err";
        }
    }

    @RequestMapping(value = "/changeLogLevel")
    @ResponseBody
    public String changeLogLevel(HttpServletRequest request){
        String level = request.getParameter("level");
        level = level.toLowerCase();

        switch (level){
            case "debug":
                LogManager.getRootLogger().setLevel(Level.DEBUG);
                break;
            case "info":
                LogManager.getRootLogger().setLevel(Level.INFO);
                break;
            default:
                LogManager.getRootLogger().setLevel(Level.INFO);
        }
        return "succ";
    }

    @RequestMapping(value = "/changeLogLevelS")
    @ResponseBody
    public String changeLogLevelS(HttpServletRequest request){
        String level = request.getParameter("level");
        level = level.toLowerCase();
        Enumeration<org.apache.log4j.Logger> loggers  =LogManager.getCurrentLoggers();
        while(loggers.hasMoreElements()){
            org.apache.log4j.Logger logger = (org.apache.log4j.Logger)loggers.nextElement();//调用nextElement方法获得元素
            System.out.println(logger.getName());
        }
        switch (level){
            case "debug":
                LogManager.getLogger("log1").setLevel(Level.DEBUG);
                break;
            case "info":
                LogManager.getLogger("log1").setLevel(Level.INFO);
                break;
            default:
                LogManager.getLogger("log1").setLevel(Level.INFO);
        }
        return "succ";
    }
}
