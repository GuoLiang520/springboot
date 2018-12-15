package com.luo.controller;

import com.alibaba.fastjson.JSONObject;
import com.luo.entity.Student;
import com.luo.serviceImpl.StudentService;
import com.luo.util.StringUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    private static org.apache.log4j.Logger logger1 = org.apache.log4j.Logger.getLogger(StudentController.class);

    private static Logger logger2 = LoggerFactory.getLogger("log1");

    /**
     * 展示所有学生
     * @param model
     * @return
     */
    @RequestMapping(value = "/showAll")
    public Object studentTest(Model model) {
        /*logger.info(" info show all students");
        logger.debug(" debug show all students");
        logger.error(" error show all students");*/
        logger2.info("show all students");
        logger2.debug("show all students");
        logger2.error("show all students");
        //logger1.info(" Show All Students");
        List<Student> students = studentService.getAllStudent();
        model.addAttribute("students",students);
        return "hello/hello";
    }

    /**
     * 所有学生 json返回
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/allStudentJson")
    public void allStudentJson(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        List<Student> students = studentService.getAllStudent();
        jsonObject.put("total",students.size());
        jsonObject.put("rows",students);
        response.getWriter().print(jsonObject.toString());
    }

    /**
     * 分页测试方法
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/pageTest")
    public void pageTest( HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        //bootStrap table offset为开始的记录数，并不是页数
        String start = request.getParameter("offset");
        String limit = request.getParameter("limit");
        if(StringUtil.isEmpty(start)){
            start = "0";
        }
        if(StringUtil.isEmpty(limit)){
            limit = "10";
        }
        JSONObject jsonObject = studentService.getAllStudentPage(Integer.parseInt(start),Integer.parseInt(limit));
        response.getWriter().print(jsonObject.toString());
        //return "hello";
    }

    /**
     * 保存学生信息
     * @param student
     * @return
     */
    @RequestMapping(value = "/saveStudent",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(Student student){
        String rtn = studentService.saveStudent(student);
        return  rtn;
    }

    /**
     * POI导出excel
     * @param response
     */
    @RequestMapping(value = "/exportStudent",method = RequestMethod.GET)
    public void exportStudent(HttpServletResponse response){
        Workbook workbook = studentService.exportStudent(response);
        try {
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        }catch (Exception e){
            logger.error("导出错误", e);
        }
    }

    /**
     * JXL导出excel
     * @param response
     */
    @RequestMapping(value = "/exportJxl",method = RequestMethod.GET)
    public void exportJxl(HttpServletResponse response){
        studentService.exportStudentJxl(response);
    }
}
