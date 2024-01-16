package com.example.testsecurity.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class JoinDTO {

    private String username;
    private String password;
}