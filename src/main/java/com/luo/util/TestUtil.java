package com.luo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TestUtil {

    private static JdbcTemplate jdbcTemplate =(JdbcTemplate) SpringUtil.getBean("jdbcTemplate");

    public static void testUse(){
        String sql = "SELECT * FROM STUDENT";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        for(Map<String,Object> map: list){
            System.out.println(StringUtil.trim(map.get("id")));
        }
    }
}
