package com.luo.dao;

import com.luo.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Repository
public class StudentDao1 {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Transactional
    public Student getStudentById(long id){
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Session session = sessionFactory.getCurrentSession();
        return  session.get(Student.class,new Long(id));
    }
}
