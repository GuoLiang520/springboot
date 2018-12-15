package com.luo.dao;

import com.luo.entity.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcDao {

    @Resource
    private JdbcTemplate jdbcTemplate;


    public  void getStudentById(){
        String sql="select * from student where id=1";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.print(list.get(0).get("name"));
    }

    public void saveStudent(Student student){
        String sql = "insert into student(name,age) value(?,?)";
        Object[] args = new Object[]{student.getName(),student.getAge()};
        jdbcTemplate.update(sql,args);
    }
    public List<Map<String,Object>> getAllStudent(){
        String sql="select age,name,password  from student order by id";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
}
