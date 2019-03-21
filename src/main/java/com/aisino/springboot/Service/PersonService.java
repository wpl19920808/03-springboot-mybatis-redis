package com.aisino.springboot.Service;

import com.aisino.springboot.model.Person;

import java.util.List;

public interface PersonService {
    public List<Person> getAllPerson();

    public int update();
}
