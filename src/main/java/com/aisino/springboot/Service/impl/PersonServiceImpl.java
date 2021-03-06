package com.aisino.springboot.Service.impl;

import com.aisino.springboot.Service.PersonService;
import com.aisino.springboot.mapper.PersonMapper;
import com.aisino.springboot.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonMapper personMapper;


    /**
     * 注入springboot自动配置好的redisTemplate
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 带有缓存的查询
     * @return
     * 方法1：synchronized同步锁可以加在整个函数，但是效率低，不使用
     */
    @Override
    public /*synchronized*/ List<Person> getAllPerson() {
        //字符串序列化器，让redis中存储的数据更有可读性，否则存储的事\xAC\xED\x00\x05t\x00\x09allPerson这种形式
        //只需要把key进行序列化即可
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        //高并发条件下，此处有问题：缓存穿透
        //查询换成是否存在这个list
        //方法2：双重检测锁
        List<Person> personList = (List<Person>)redisTemplate.opsForValue().get("allPerson");
        if(null == personList){
            synchronized (this){
                //从redis获取一下
                personList = (List<Person>)redisTemplate.opsForValue().get("allPerson");

                if(null == personList){
                    System.out.println("query database");
                    //缓存为空，查询数据库
                    personList = personMapper.selectAllPerson();
                    //把数据库查询出来的数据放入redis
                    redisTemplate.opsForValue().set("allPerson", personList);
                }else{
                    System.out.println("query redis");
                }
            }
        }else {
            System.out.println("query redis");
        }
        return personList;
    }

    @Transactional// 事务
    @Override
    public int update() {
        Person person = new Person();
        person.setId(1);
        person.setName("update");
        person.setAge(12);
        person.setSex("女");
        int updateNum = personMapper.updateByPrimaryKeySelective(person);
        System.out.println("rows: " + updateNum);

        int a = 10/0;//故意抛出异常，测试事务回滚，除数不能为0

        return updateNum;
    }
}
