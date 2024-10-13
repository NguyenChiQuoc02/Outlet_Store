package com.itbk.Outlet_Store.repository;


import com.itbk.Outlet_Store.domain.CartItem;
import com.itbk.Outlet_Store.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartItemAndSizeAndColor(Product product, String size, String color);

    @Query("SELECT SUM(ci.quantity * (ci.unitPrice * (100 - ci.discount) / 100)) FROM CartItem ci")
    Integer getTotalPrice();
}
