package com.luo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private  RedisTemplate<String,Object> redisTemplate ;



    /**
     * redis 普通存值
     * @param key key
     * @param object value
     * @return 保存成功与否
     */
    public void saveObject(String key,Object object){
        try {
            redisTemplate.opsForValue().set(key, object);
        }catch (Exception e){
            logger.error("redis 保存Object错误", e);
        }
    }

    /**
     * 保存Map到redis
     * @param key key
     * @param map val
     */
    public void saveMap(String key, Map map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
        }catch (Exception e){
            logger.error("redis 保存Object错误", e);
        }
    }

    /**
     * 获取普通key只
     * @param key key
     * @return obj
     */
    public Object getVal(String key){
        try {
            return  redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            logger.error("redis 保存Object错误", e);
        }
        return  "";
    }

    /**
     * 获取map类型
     * @param key key
     * @return duix
     */
    public Object getMap(String key){
        try {
            return  redisTemplate.opsForHash().entries(key);
        }catch (Exception e){
            logger.error("redis 保存Object错误", e);
        }
        return  "";
    }

    /**
     * 删除对象
     * @param key key
     * @return 是否删除
     */
    public Object deleteObject(String key){
        try {
            return  redisTemplate.delete(key);
        }catch (Exception e){
            logger.error("redis 保存Object错误", e);
        }
        return  "";
    }

    /**
     * 重命名
     * @param key 新名称
     * @param oldKey 老名称
     * @return
     */
    public Object rename(String key,String oldKey){
        try {
            redisTemplate.rename(oldKey, key);
        }catch (Exception e){
            logger.error("redis 保存Object错误", e);
        }
        return  "";
    }

    /**
     * 设置存在时间 秒为单位 1 标识1s
     * @param key key
     * @param time 存时间
     * @return
     */
    public Object expire(String key,long time){
        try {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }catch (Exception e){
            logger.error("redis 保存Object错误", e);
        }
        return  "";
    }

    /**
     * 批量获取值
     * @param keys keys
     * @return list<Object>
     */
    public List<Object> multiGet(Collection<String> keys){
        List<Object> list = new ArrayList<>();
        try {
            list =  redisTemplate.opsForValue().multiGet(keys);
        }catch (Exception e){
            logger.error("redis 保存Object错误", e);
        }
        return  list;
    }
}
