package com.itbk.Outlet_Store.repository;


import com.itbk.Outlet_Store.domain.ERole;
import com.itbk.Outlet_Store.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
