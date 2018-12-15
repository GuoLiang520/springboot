package com.luo.controller;

import com.luo.entity.ZtreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "clazz")
public class ClazzController {

    private static Logger logger = LoggerFactory.getLogger(ClazzController.class);

    @RequestMapping(value = "/toZtree")
    public String toZtree() {
        return "clazz/ztreeTest.html";
    }

    /**
     * 展示树
     * @return json
     */
    @RequestMapping(value = "/getAllClazz",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public List getAllClazz(){
        List<ZtreeNode> list = new ArrayList<>();
        ZtreeNode ztreeNode1 = new ZtreeNode("1", "0", "年级");
        ZtreeNode ztreeNode2 = new ZtreeNode("2", "1", "2018");
        ZtreeNode ztreeNode3 = new ZtreeNode("3", "1", "2019");
        ZtreeNode ztreeNode4 = new ZtreeNode("4", "2", "学生1");
        ZtreeNode ztreeNode5 = new ZtreeNode("5", "3", "学生2");
        ZtreeNode ztreeNode6 = new ZtreeNode("6", "2", "学生3");
        ZtreeNode ztreeNode7 = new ZtreeNode("7", "3", "学生4");
        list.add(ztreeNode1);
        list.add(ztreeNode2);
        list.add(ztreeNode3);
        list.add(ztreeNode4);
        list.add(ztreeNode5);
        list.add(ztreeNode6);
        list.add(ztreeNode7);
        return list;
    }
}
