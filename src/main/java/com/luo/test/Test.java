package com.luo.test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.*;

public class Test {
    //作为synchronized的对象监视器

    public static void main(String[] args) throws InterruptedException {
       /* Box box = new Box(5);
        new Thread(new Consumer(box)).start();
        new Thread(new Producer(box)).start();*/
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10));
        for(int i=0;i<20;i++){
            CallableImpl callable = new CallableImpl(i);
            FutureTask futureTask = new FutureTask(callable);
            executor.execute(futureTask);
        }

        while (executor.getActiveCount()>0){
            System.out.println("活线程数："+executor.getActiveCount());
            Thread.sleep(1000);
        }
    }
}

class Box{
    private Integer count;
    public Box(Integer count){
        this.count = count;
    }
    public void consum(){
        while (true) {
            synchronized (count) {
                try {
                    if (count == 0) {
                        wait();
                    }
                    count--;
                    System.out.println("consum");
                    notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void produce(){
        while (true) {
            synchronized (count) {
                try {
                    if (count >= 5) {
                        wait();
                    }
                    count++;
                    System.out.println("produce");
                    notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Producer implements Runnable{
    private Box box;
    public Producer(Box box){
        this.box = box;
    }

    @Override
    public void run() {
        box.produce();
    }
}

class Consumer implements Runnable{
    private Box box;
    public Consumer(Box box){
        this.box = box;
    }

    @Override
    public void run() {
        box.consum();
    }
}

class CallableImpl implements Callable<Integer>{

    private int i;

    public CallableImpl(int i) {
        this.i = i;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(10000);
        System.out.println(i);
        return i;
    }
}
