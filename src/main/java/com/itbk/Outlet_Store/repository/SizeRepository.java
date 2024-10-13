package com.itbk.Outlet_Store.repository;


import com.itbk.Outlet_Store.domain.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    Page<Size> findAll(Pageable pageable);
    // SizeProduct
    List<Size> findBySizeProductProductId(Long productId);
}
