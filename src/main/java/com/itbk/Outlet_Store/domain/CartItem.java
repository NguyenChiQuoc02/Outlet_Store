package com.itbk.Outlet_Store.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product cartItem;

    private String size;

    @Column(columnDefinition = "nvarchar(50)")
    private String color;

    private int quantity;

    private String image;

    private String description;

    private int discount;

    private int unitPrice;

    @Column(columnDefinition = " nvarchar(150)")
    private String name;
}
