package com.luo.serviceImpl;

import com.luo.service.Office;

public class Word implements Office {
    @Override
    public void start() {
        System.out.println("This is Word");
    }
}
