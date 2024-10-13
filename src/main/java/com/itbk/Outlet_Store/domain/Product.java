package com.itbk.Outlet_Store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(columnDefinition = "nvarchar(200) not null")
	private String name;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private int unitPrice;

	@Column(length = 400)
	private String image;

	@Column(columnDefinition = "nvarchar(500) not null")
	private String description;

	@Column(nullable = false)
	private int discount;

	@Column(name = "xuat_xu", columnDefinition = "nvarchar(50)")
	private String xuatXu;

	@Temporal(TemporalType.DATE)
	private Date enteredDate;

	// fetch = FetchType.LAZY tức là khi bạn find, select đối tượng
	// Company từ database thì nó sẽ không lấy các đối tượng Employee liên quan

	@ManyToOne()
	@JoinColumn(name = "categoryId")
	private Category category;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetail;

	@JsonIgnore
	@OneToMany(mappedBy = "sizeProduct", cascade = CascadeType.ALL)
	private List<Size> sizeProduct;

	@JsonIgnore
	@OneToMany(mappedBy = "colorProduct", cascade = CascadeType.ALL)
	private List<Color> colorProduct;

	@JsonIgnore
	@OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL)
	private List<CartItem> cartItem;
}