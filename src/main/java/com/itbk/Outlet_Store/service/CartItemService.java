package com.itbk.Outlet_Store.service;

import com.itbk.Outlet_Store.domain.*;
import com.itbk.Outlet_Store.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;

  public CartItemService(CartItemRepository cartItemRepository,
                         ProductRepository productRepository,
                         UserRepository userRepository,
                         OrderRepository orderRepository,
                         OrderDetailRepository orderDetailRepository) {
    this.cartItemRepository = cartItemRepository;
    this.productRepository  = productRepository;
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
    this.orderDetailRepository = orderDetailRepository;
  }

  public int getTotalPrice(){
    return this.cartItemRepository.getTotalPrice();
  }

  public List<CartItem>  getAllCartItem(){
    return this.cartItemRepository.findAll();
  }

  public int getCount(){
    return this.cartItemRepository.findAll().size();
  }

  public void deleteAllCartItem(){
    this.cartItemRepository.deleteAll();
  }

  public CartItem addToCart(CartItem cartItem){

    Optional<Product> optionalProduct = productRepository.findById(cartItem.getCartItem().getProductId());

    if (optionalProduct.isPresent()) {

      Product product = optionalProduct.get();
      CartItem newCartItem = new CartItem();

      List<CartItem> existingItems = cartItemRepository.findByCartItemAndSizeAndColor(product, cartItem.getSize(), cartItem.getSize());

      if (!existingItems.isEmpty()) {
        CartItem existingItem = existingItems.get(0);
        existingItem.setQuantity(existingItem.getQuantity() + 1);
        cartItemRepository.save(existingItem);
      } else {
        newCartItem.setCartItem(product);
        newCartItem.setColor(cartItem.getColor());
        newCartItem.setSize(cartItem.getSize());
        newCartItem.setQuantity(1);
        newCartItem.setImage(product.getImage());
        newCartItem.setName(product.getName());
        newCartItem.setUnitPrice(product.getUnitPrice());

        cartItemRepository.save(newCartItem);
      }

      return newCartItem;
    } else {
      return null;
    }
  }

  public CartItem getCartItemById(long id){
      Optional<CartItem> cartItem = this.cartItemRepository.findById(id);
      if(cartItem.isPresent()){
        return cartItem.get();
      }
      return  null;
  }
  public CartItem updateToCart(CartItem cartItem, long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);

    if (optionalProduct.isEmpty()) {
      throw new RuntimeException("Product with ID " + id + " not found");
    }

    Product product = optionalProduct.get();
    List<CartItem> existingItems = cartItemRepository.findByCartItemAndSizeAndColor(
            product, cartItem.getSize(), cartItem.getColor());

    CartItem existingItem;
    if (!existingItems.isEmpty()) {
      // Nếu đã có sản phẩm cùng size và color trong giỏ hàng, thì cập nhật
      existingItem = existingItems.get(0);
      existingItem.setQuantity(cartItem.getQuantity());
      existingItem.setColor(cartItem.getColor());
      existingItem.setSize(cartItem.getSize());
      this.cartItemRepository.save(existingItem);
    } else {
      // Nếu chưa có, tạo mới một CartItem
      existingItem = new CartItem();
//      existingItem.setCartItem(cartItem.setId(id));
      existingItem.setQuantity(cartItem.getQuantity());
      existingItem.setColor(cartItem.getColor());
      existingItem.setSize(cartItem.getSize());
//      this.cartItemRepository.save(existingItem);
    }

    return existingItem;
  }


  public void deleteFromCart(CartItem cartItem){
    Optional<Product> optionalProduct = productRepository.findById(cartItem.getCartItem().getProductId());
    CartItem existingItem = getCartItemById(cartItem.getId());
    if (optionalProduct.isPresent()) {
      Product product = optionalProduct.get();
      List<CartItem> existingItems = cartItemRepository.findByCartItemAndSizeAndColor(product, cartItem.getSize(), cartItem.getSize());
      if (!existingItems.isEmpty()) {
        existingItem = existingItems.get(0);
        cartItemRepository.delete(existingItem);
      }
    }
  }

  public String checkout(Order orderCk){
    String redirectUrl = "";
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();

    Optional<User> currentUser = userRepository.findByUsername(currentUsername);

    if (currentUser.isPresent()) {

      Order order = new Order();
      order.setUsername(currentUser.get());
      order.setAddress(orderCk.getAddress());
      order.setPhone(orderCk.getPhone());
      order.setPaymentMethod(orderCk.getPaymentMethod());
      order.setOrderDate(new Date());
      // order.setUsername(userDetails.getUsername());
      order.setTotal(orderCk.getTotal());
      this.orderRepository.save(order);

      // Get list of cart items for the user
      List<CartItem> cartItems = cartItemRepository.findAll();

      // Create order detail objects and save them to the database
      for (CartItem cartItem : cartItems) {
        Optional<Product> optionalProduct = this.productRepository
                .findById(cartItem.getCartItem().getProductId());

        if (optionalProduct.isPresent()) {
          Product product = optionalProduct.get();

          OrderDetail orderDetail = new OrderDetail();
          orderDetail.setOrder(order);
          orderDetail.setProduct(product);
          orderDetail.setQuantity(cartItem.getQuantity());
          orderDetail.setUnitPrice(
                  (product.getUnitPrice() - (product.getUnitPrice() * product.getDiscount() / 100)));
          orderDetail.setColor(cartItem.getColor());
          orderDetail.setSize(cartItem.getSize());
          this.orderDetailRepository.save(orderDetail);
        }
      }

      this.cartItemRepository.deleteAll(cartItems);

      System.out.println("tong tien: " + orderCk.getTotal());
//      if (orderCk.getPaymentMethod().equals("vnpay")) {
//        redirectUrl = vnpayService.createPayment(total, request);
//      } else {
//        redirectUrl = "http://localhost:3000/";
//      }

      redirectUrl ="http://localhost:3000/";
//      System.out.println("redirectUrl: " + redirectUrl);

    }

    return redirectUrl;
  }


}
