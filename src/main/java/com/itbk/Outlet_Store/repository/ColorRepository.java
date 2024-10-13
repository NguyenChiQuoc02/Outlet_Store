package com.itbk.Outlet_Store.repository;


import com.itbk.Outlet_Store.domain.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

	Page<Color> findAll(Pageable pageable);

	List<Color> findByColorProductProductId(Long productId);
}
