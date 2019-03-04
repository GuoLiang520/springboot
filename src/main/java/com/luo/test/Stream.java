package com.luo.test;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Stream {

    public static void main(String[] args) throws IOException {
       /* String s = new String(Files.readAllBytes(Paths.get("E:\\JavaFile\\23.doc")));
        System.out.println(s);*/
        bufferTest();
        channelTest();
    }

    /**
     * 目录文件路径
     * @param filePath 压缩目录或文件
     * @param zipPath zip存放路径
     */
    public static void  zipMain(String filePath,String zipPath) {
        File file = new File(filePath);
        try {
            String[] paths =  file.list();
            //zip输出流
            ZipOutputStream zout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipPath)));
            if(file.isFile()){//文件
                zip(file.getParent(),file.getName(), file.getParent().length(), zout);
            }else{//目录
                for(String s : paths){
                    zip(filePath,s, file.getPath().length(), zout);
                }
            }
            zout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩
     * @param parentPath 父目录
     * @param name 文件名
     * @param bastpathLength 基础路径长度
     * @param out 输出流
     * @throws IOException io异常
     */
    public static void zip(String parentPath,String name,int bastpathLength,ZipOutputStream out) throws IOException {
        String path = parentPath + File.separator + name;
        File file = new File(path);
        System.out.println(path);
        if(file.isFile()){
            ZipEntry entry = new ZipEntry(path.substring(bastpathLength+1));
            out.putNextEntry(entry);
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            out(in, out);
            in.close();
        }else{
            String[] paths =  file.list();
            for(String s : paths){
                zip(path,s,bastpathLength,out);
            }
        }
    }

    /**
     * 输出文件
     * @param in 输入流
     * @param out 输出流
     * @throws IOException io异常
     */
    public static void out(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int length ;
        while ((length = in.read(buffer)) != -1){
            out.write(buffer, 0, length);
        }
    }

    private static void channelTest() throws IOException {
        long t1 = new Date().getTime();
        FileInputStream in = new FileInputStream("H:\\damo.zip");
        FileChannel channel = in.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        FileOutputStream out = new FileOutputStream("H:\\damo1.zip");
        FileChannel channel1 = out.getChannel();
        while ((channel.read(buffer)) != -1){
            buffer.flip();
            channel1.write(buffer);
            buffer.clear();
        }
        buffer.clear();
        channel.close();
        in.close();
        channel.close();
        out.close();
        long t2 = new Date().getTime();
        System.out.println("Channel="+(t2-t1));
    }

    public static void bufferTest() throws IOException {
        long t1 = new Date().getTime();
        InputStream in = new BufferedInputStream(new FileInputStream("H:\\damo.zip"));
        OutputStream out = new BufferedOutputStream(new FileOutputStream("H:\\damo2.zip"));
        byte[] buffer = new byte[1024];
        int length ;
        while ((length = in.read(buffer)) != -1){
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
        long t2 = new Date().getTime();
        System.out.println("Buffer="+(t2-t1));
    }

    public void regex(){
        Pattern pattern = Pattern.compile("1[2,3]");
        Matcher matcher = pattern.matcher("912313");
        while (matcher.find()){
            System.out.println(matcher.group());
        }
        System.out.println(matcher.find());
    }
}
