package com.example.testsecurity.controller;

import com.example.testsecurity.entity.Person;
import com.example.testsecurity.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    public void savePerson(@RequestBody Person person){
        personRepository.save(person);
    }
}
