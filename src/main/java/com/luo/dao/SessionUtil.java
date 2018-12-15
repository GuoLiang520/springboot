package com.luo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class SessionUtil {
    @Autowired
    private  EntityManagerFactory entityManagerFactory;

    private  SessionFactory sessionFactory;

    private  SessionUtil(){
    }

    public  SessionFactory getSession(){
       return entityManagerFactory.unwrap(SessionFactory.class);
    }
}
