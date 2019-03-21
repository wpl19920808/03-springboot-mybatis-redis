package com.aisino.springboot.Service.impl;

import com.aisino.springboot.Service.PersonService;
import com.aisino.springboot.mapper.PersonMapper;
import com.aisino.springboot.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<Person> getAllPerson() {
        return personMapper.selectAllPerson();
    }
}
