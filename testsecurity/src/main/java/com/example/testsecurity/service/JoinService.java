package com.example.testsecurity.service;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//회원가입 로직
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    //DTO를 Entity로 바꾸어 repository에 넣는 과정
    public boolean joinProcess(JoinDTO joinDTO){

        //user아 이미 존재하는지 검증
        boolean isUser = userRepository.existsByUserName(joinDTO.getUsername());
        if (isUser) {
            return false;
        }

        UserEntity user = new UserEntity();

        user.setUserName(joinDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));//패스워드 암호화 진행
        //회원이 직접 role 정보를 선택할 수 없는 구조이므로
        //개발자가 직접 role값을 명시적으로 넣어주어야 한다.
        user.setRole("ROLE_ADMIN"); //넣을 때 무조건 ROLE_~ 이러한 방식으로 넣어주어야 한다.


        userRepository.save(user);
        return true;
    }
}
