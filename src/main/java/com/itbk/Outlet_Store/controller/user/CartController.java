package com.itbk.Outlet_Store.controller.user;

import com.itbk.Outlet_Store.domain.CartItem;
import com.itbk.Outlet_Store.domain.Order;
import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.ResponseObject;
import com.itbk.Outlet_Store.service.CartItemService;
import com.itbk.Outlet_Store.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("home/cart")
public class CartController {

  private final CartItemService cartItemService;
  private final OrderService orderService;

  public CartController(CartItemService cartItemService,OrderService orderService) {
    this.cartItemService = cartItemService;
    this.orderService = orderService;
  }

  @GetMapping("/totalPrice")
  public Integer getTotalPrice() {
    return this.cartItemService.getTotalPrice();
  }

  @GetMapping("/list")
  public List<CartItem> getAllProducts() {
    return this.cartItemService.getAllCartItem();
  }

  @GetMapping("/count")
  public int count() {
    return this.cartItemService.getCount();
  }

  @DeleteMapping("/delete-all")
  public String deleteAll() {
    this.cartItemService.deleteAllCartItem();
    return "Deleted all cart items";
  }

  @PostMapping("/add")
  public ResponseEntity<ResponseObject> addToCart(@RequestBody CartItem cartItem){
      CartItem newCartItem = this.cartItemService.addToCart(cartItem);
      return   ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "thêm vào giở hàng thành công", newCartItem));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ResponseObject> updateFromCart(@RequestBody CartItem cartItem, @PathVariable long id){
    CartItem updateCartItem = this.cartItemService.updateToCart(cartItem,id);
    return   ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "cập nhật giở hàng thành công", updateCartItem));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteFromCart(@RequestBody CartItem cartItem){
    this.cartItemService.deleteFromCart(cartItem);
    return   ResponseEntity.ok("Sản phẩm đã được xóa khỏi giỏ hàng");
  }

  @PostMapping("/checkout")
  public ResponseEntity<ResponseObject> checkout(@RequestBody Order order){
    return   ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Thanh toán thành công", this.cartItemService.checkout(order)));
  }
  @GetMapping("/historyorder")
  public ResponseEntity<PageResult<Order>> getorderByUser(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
    PageResult<Order> order = this.orderService.getorderByUser(page, size);
    return ResponseEntity.ok(order);
  }


}
