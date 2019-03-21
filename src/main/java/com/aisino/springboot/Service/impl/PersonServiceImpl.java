package com.aisino.springboot.Service.impl;

import com.aisino.springboot.Service.PersonService;
import com.aisino.springboot.mapper.PersonMapper;
import com.aisino.springboot.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<Person> getAllPerson() {
        return personMapper.selectAllPerson();
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
