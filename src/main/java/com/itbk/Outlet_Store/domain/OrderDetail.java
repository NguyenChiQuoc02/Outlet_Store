package com.itbk.Outlet_Store.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderdetails")
public class OrderDetail implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderDetailId;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "unit_price")
	private Integer unitPrice;

	@ManyToOne()
	@JoinColumn(name = "productId")
	private Product product;

	@Column(name = "color", columnDefinition = "nvarchar(50)")
	private String color;

	@Column(name = "size", columnDefinition = "nvarchar(50)")
	private String size;

	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;
}
