package com.example.willhabenfake.Repositories;

import com.example.willhabenfake.Models.Product;
import com.example.willhabenfake.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByUser(User user);

    List<Product> findAllByUserId(Long user_id);



}
