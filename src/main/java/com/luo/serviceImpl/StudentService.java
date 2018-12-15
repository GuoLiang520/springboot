package com.luo.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.luo.dao.JdbcDao;
import com.luo.dao.StudentDao;
import com.luo.entity.Student;
import com.luo.util.ExcelUtil;
import com.luo.util.JxlUtil;
import com.luo.util.MysqlPaginationUtil;
import com.luo.util.StringUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao; //基于JPA

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /*@Autowired
    private EntityManagerFactory entityManagerFactory;*/

    @org.springframework.transaction.annotation.Transactional
    public Student getStudentById(int id) throws Exception {
        Student student = new Student();
        student.setId(3);
        student.setName("郭亮");
        student.setPassword("123");
        student.setAge("23");
        student.setBirthday(new Date());

        Student student1 = new Student();
        student1.setId(4);
        student1.setName("周华");
        student1.setPassword("mmm");
        student1.setAge("mmm");
        student1.setBirthday(new Date());

        studentDao.save(student);
        studentDao.save(student1);
        if(1!=1) {
            throw new Exception("1231");
        }
        Student student2 = studentDao.getOne(new Long(1));
        jdbcDao.getStudentById();
        return  student2;
    }

    /**
     * 保存学生信息
     * @param student
     */
    @Transactional
    public String saveStudent(Student student){
        try {
            Student s = studentDao.save(student);
            return "succ";
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("保存学生信息错误");
        }
    }

    /**
     * 获取全部学生
     * @return
     */
    @Transactional
    public List<Student> getAllStudent(){
        List<Student> list = studentDao.findAll();
        return list;
    }

    /**
     * 获取全部学生
     * @return
     */
    @Transactional
    public JSONObject getAllStudentPage(int start, int limit){
        JSONObject jsonObject = new JSONObject();
        String queryField = "id,name,age,birthday";
        String sql = "select #query# from student order by id";
        MysqlPaginationUtil mysqlPaginationUtil = new MysqlPaginationUtil();
        mysqlPaginationUtil.setStart(start);
        mysqlPaginationUtil.setLimit(limit);
        mysqlPaginationUtil.setJdbcTemplate(jdbcTemplate);
        mysqlPaginationUtil.setCountSql(sql.replace("#query#","count(1)"));
        mysqlPaginationUtil.setQuerySql(sql.replace("#query#",queryField));
        int count = mysqlPaginationUtil.getTotal();
        List<Map<String,Object>> list = mysqlPaginationUtil.getList();
        List<Student> students = new ArrayList<>();
        Student student = null;
        for(Map map:list){
            student = new Student();
            student.setId(Integer.parseInt(StringUtil.trim(map.get("id"))));
            student.setName(StringUtil.trim(map.get("name")));
            student.setAge(StringUtil.trim(map.get("age")));
            student.setBirthday(new Date());
            students.add(student);
        }
        jsonObject.put("total",count);
        jsonObject.put("rows",students);
        return jsonObject;
    }

    /**
     * POI导出excel
     * @param response
     * @return
     */
    public Workbook exportStudent(HttpServletResponse response){
        List<Map<String,Object>> list =jdbcDao.getAllStudent();
        String[] heads = {"姓名","年龄","密码"};
        String[] parms = {"name","age","password"};
        int[] widths = {20,30,40};
        return ExcelUtil.genbody(response,list,"学生信息表","xlsx",heads,parms,widths);
    }
    /**
     * JXL导出excel
     * @param response
     * @return
     */
    public void exportStudentJxl(HttpServletResponse response){
        List<Map<String,Object>> list =jdbcDao.getAllStudent();
        JxlUtil.export(response, list, "学生信息表");
    }
}
