package com.itbk.Outlet_Store.repository;


import com.itbk.Outlet_Store.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAll(Pageable pageable);
    Page<Order> findByUsername_Username(String username, Pageable pageable);

}