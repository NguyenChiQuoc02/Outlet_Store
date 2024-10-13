package com.itbk.Outlet_Store.repository;


import com.itbk.Outlet_Store.domain.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeProductRepository extends JpaRepository<TypeProduct, Long> {

}
