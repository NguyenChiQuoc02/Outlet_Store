package com.itbk.Outlet_Store.repository;


import com.itbk.Outlet_Store.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<User, Long> {

}
