package com.example.willhabenfake.Repositories;

import com.example.willhabenfake.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);



}
