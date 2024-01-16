package com.example.testsecurity.repository;

import com.example.testsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    //첫번째 인자값은 entity, 두번째 인가잢은 id의 타입

    //중복 검증
    boolean existsByUserName(String userName);

    //
    UserEntity findByUserName(String userName);
}
