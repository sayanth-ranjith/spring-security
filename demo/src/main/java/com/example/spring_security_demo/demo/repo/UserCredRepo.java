package com.example.spring_security_demo.demo.repo;

import com.example.spring_security_demo.demo.entity.UserCredInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredRepo extends JpaRepository<UserCredInfo, Long> {
    Optional<UserCredInfo> findByUsername(String username);
}
