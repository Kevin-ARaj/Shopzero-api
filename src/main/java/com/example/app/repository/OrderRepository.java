package com.example.app.repository;

import com.example.app.entity.Orders;
import com.example.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUser(User user);
}