package com.luo.test;


import com.luo.util.HttpClientUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Test {
    //作为synchronized的对象监视器

    public static void main(String[] args) throws InterruptedException, IOException {

       /* Map<String,String> heads = new HashMap<>();
        heads.put("test", "md5");
        String rtn = HttpClientUtil.getPost("http://127.0.0.1:10001/ab/a", heads,"周华");
        System.out.println(rtn);*/
        //System.out.println(HttpClientUtil.inToString(in));
       /* Box box = new Box(5);
        new Thread(new Consumer(box)).start();
        new Thread(new Producer(box)).start();*/
        /*ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10));
        for(int i=0;i<20;i++){
            CallableImpl callable = new CallableImpl(i);
            FutureTask futureTask = new FutureTask(callable);
            executor.execute(futureTask);
        }

        while (executor.getActiveCount()>0){
            System.out.println("活线程数："+executor.getActiveCount());
            Thread.sleep(1000);
        }*/

        //FtpEntity ftp = new FtpEntity("192.168.6.129", 21, "uftp", "uftp");

        /*InputStream in = new BufferedInputStream(new FileInputStream("E:/TestFile/最高院执行流程系曾用名接口需求20180822（丁瑞峰）.doc"));

        FTPUtil.uploadFile(ftp, "file/测试", "最高院执行流程系曾用名接口需求20180822（丁瑞峰）.doc", in);
        in.close();*/


        /*InputStream in = new BufferedInputStream(new FileInputStream("E:\\TestFile\\最高院执行流程系曾用名接口需求20180822（丁瑞峰）.doc"));

        FTPUtil.uploadFile(ftp, "file/测试1", "测试.doc", in);
        in.close();*/
        //FTPUtil.deleteFile(ftp, "file/b", "a.txt", true);

        /*OutputStream out = new FileOutputStream(new File("E:\\TestFile\\测试.doc"));
        FTPUtil.downloadFile(ftp, "file/测试1", "测试.doc", out);
        out.close();*/
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
        Thread.sleep(1000);
        System.out.println(i);
        return i;
    }
}
