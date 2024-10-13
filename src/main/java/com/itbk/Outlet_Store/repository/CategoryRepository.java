package com.itbk.Outlet_Store.repository;


import com.itbk.Outlet_Store.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category findByName(String name);

	public List<Category> findByNameContaining(String name);

	public Page<Category> findByNameContaining(String name, Pageable pageable);

	public List<Category> findByidTypeProduct_Id(Long idTypeProduct);
}
