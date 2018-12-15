package com.luo.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class FileUtil {
    /**
     * NIO读文件
     * @param path 路径
     * @return
     * @throws IOException
     */
    public static String getFile(String path) throws IOException {
        StringBuffer sb =new StringBuffer();
        File file = new File(path);
        FileInputStream in = null;
        FileChannel channel = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        Charset charset = Charset.forName("GBK");
        CharsetDecoder charsetDecoder =  charset.newDecoder();
        if(!file.exists() || !file.isFile()){//判断文件是否存在
            return "文件不存在";
        }
        try {
            in = new FileInputStream(file);
            channel = in.getChannel();
            while((int)channel.read(byteBuffer) !=-1){
                byteBuffer.flip();//position = 0
                charsetDecoder.decode(byteBuffer,charBuffer,true);
                charBuffer.flip();
                while (charBuffer.remaining()>0){
                    sb.append(charBuffer.get());
                }
                byteBuffer.clear();
                charBuffer.clear();
            }
        }catch (IOException e){
            throw e;
        }finally {
            if(in != null){
                in.close();
            }
        }
        return sb.toString();
    }

    /**
     * IO流读文件
     * @param path 文件路径
     * @return
     */
    public static byte[] getByteFromFile(String path) {
        byte[] b = null;

        File f = new File(path);
        if (f.exists() && f.isFile()) {
            InputStream fis = null;
            ByteArrayOutputStream out = null;
            try {
                fis = new BufferedInputStream(new FileInputStream(f));
                out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int n = 0;
                while ((n = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, n);
                }
                b = out.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return b;
    }
}
