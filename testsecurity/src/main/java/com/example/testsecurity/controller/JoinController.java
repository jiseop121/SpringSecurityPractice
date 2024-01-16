package com.example.testsecurity.controller;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }


    @GetMapping("/join")
    public String joinPage(){
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){

        if(joinService.joinProcess(joinDTO)==false){
            System.out.println("Join Fail!!");
            return "redirect:/join";
        }

        return "redirect:/login";
        //원래는 회원가입이 완료되면 login으로 리다이렉트
        //회원가입이 실패하면 다시 join페이지로 이동해야하지만
        //지금은 연습이므로 단순화함.
    }
}

