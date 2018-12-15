package com.luo.util;

public class StringUtil {

    /**
     * TRIM 将字符串对象去空格或者NULL对象变""
     * @param object
     * @return
     */
    public static String trim(Object object){
        if (null == object){
            return "";
        }
        String s = String.valueOf(object);
        if( null == s || "null"==s ||"undefined" == s){
            return "";
        }
        return s.trim();
    }

    public static boolean isEmpty(Object object){
        String s = trim(object);
        if("".equals(s)){
            return  true;
        }
        return false;
    }
}
