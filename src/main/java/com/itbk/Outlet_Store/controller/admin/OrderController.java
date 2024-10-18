package com.itbk.Outlet_Store.controller.admin;

import com.itbk.Outlet_Store.domain.Order;
import com.itbk.Outlet_Store.domain.OrderDetail;
import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.repository.OrderDetailRepository;
import com.itbk.Outlet_Store.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("admin/order")
public class OrderController {

  private final OrderService orderService;
  private final OrderDetailRepository orderDetailRepository;

  public OrderController(OrderService orderService, OrderDetailRepository orderDetailRepository) {
    this.orderService = orderService;
    this.orderDetailRepository = orderDetailRepository;
  }

  @GetMapping("")
  public ResponseEntity<PageResult<Order>> getOrder(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size) {
    PageResult<Order> order = this.orderService.getorder(page, size);
    return ResponseEntity.ok(order);
  }

  @GetMapping("find/{orderId}")
  public List<OrderDetail> getAllOrderDetails(@PathVariable("orderId") Long orderId) {

    return this.orderDetailRepository.findByOrderOrderId(orderId);
  }
}
