package com.itbk.Outlet_Store.service;

import com.itbk.Outlet_Store.domain.Order;
import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public PageResult<Order> getorder(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Order> order;
    order = this.orderRepository.findAll(pageable);
    return new PageResult<>(order.getContent(), order.getTotalPages());
  }

  public PageResult<Order> getorderByUser(int page, int size) {
    // xác định trang hiện tại và kích thước của trang muốn lấy.

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Pageable pageable = PageRequest.of(page, size);
    Page<Order> order;
    order = this.orderRepository.findByUsername_Username(authentication.getName(),pageable);
    // products.getContent() là một phương thức của đối tượng Page<Product>
    // trả về danh sách các sản phẩm trên trang hiện tại của kết quả phân trang.
    return new PageResult<>(order.getContent(), order.getTotalPages());
  }
}
