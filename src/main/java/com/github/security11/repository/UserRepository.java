package com.github.security11.repository;

import com.github.security11.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//CRUD  함수를  JpaRepository 가 들고 있음.
//@Repository 라는 어노테이션이 없어도 IoC 의존성됨 . 이유는 JpaRepository 상속 받아서
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
