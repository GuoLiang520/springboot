package com.luo.test;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //executorService();
        //pool();
        poolPro();
    }

    public static void executorService() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        ArrayList<Future<Integer>>  callBack = new ArrayList<>();
        for (int i =1;i<30;i++){
            Calculate calculate = new Calculate();
            calculate.setI(i);
            //pool.submit(calculate);
            callBack.add(pool.submit(calculate));
        }
        for (Future<Integer> future : callBack){
            System.out.println("得分："+future.get());
        }
        pool.shutdown();
    }

    public static void  pool() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 15, 3000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        ArrayList<Future<Integer>>  callBack = new ArrayList<>();
        for (int i =1;i<30;i++){
            Calculate calculate = new Calculate();
            calculate.setI(i);
            //pool.submit(calculate);
            callBack.add(pool.submit(calculate));
        }
        System.out.println(pool.getQueue().size());
        /*for (Future<Integer> future : callBack){
            System.out.println("得分："+future.get());
        }*/
        //pool.shutdown();
    }

    public static void  poolPro() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 15, 3000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        ArrayList<Future<Integer>>  callBack = new ArrayList<>();
        AddNum o = AddNum.getAddNum();
        for (int i =1;i<30;i++){
            CalculatePro calculate = new CalculatePro();
            calculate.setI(i);
            calculate.setObject(o);
            callBack.add(pool.submit(calculate));
        }
        System.out.println(o);
        /*for (Future<Integer> future : callBack){
            System.out.println("得分："+future.get());
        }*/
        pool.shutdown();
    }
}

class Calculate implements Callable {

    private  int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(((45/5)+1)*1000);
        System.out.println("参数："+i);
        return i;
    }
}

class CalculatePro implements Callable {

    private  int i;

    private  AddNum object;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(AddNum object) {
        this.object = object;
    }

    @Override
    public Integer call() throws Exception {
        synchronized (object){
            System.out.println("参数："+i+"  "+object.getAdd());
            System.out.println("结果："+(i+object.getAdd()));
            object.setAdd((object.getAdd())+1);
        }

        return i+1;
    }
}

class AddNum {
    private int add;
    private static AddNum addNum;
    private AddNum(){
        add = 1;
    }
    static {
        addNum = new AddNum();
        addNum.setAdd(5);
    }
    public static  AddNum getAddNum(){
        return addNum;
    }

    public int getAdd() {
        return add;
    }

    public void setAdd(int add) {
        this.add = add;
    }
}