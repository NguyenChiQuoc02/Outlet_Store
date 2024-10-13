package com.itbk.Outlet_Store.repository;


import com.itbk.Outlet_Store.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
	
	
	List<OrderDetail> findByOrderOrderId(Long orderId);

}
