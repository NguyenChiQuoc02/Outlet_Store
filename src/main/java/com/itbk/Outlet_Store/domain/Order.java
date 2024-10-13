package com.itbk.Outlet_Store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Temporal(TemporalType.DATE)
	private Date orderDate;

	@Column(name = "payment_method", columnDefinition = "nvarchar(150)")
	private String paymentMethod;

	@Column(name = "address", columnDefinition = "nvarchar(150)")
	private String address;

	@Column(name = "phone")
	private String phone;

	@Column(name = "total")
	private Long total;

	@JsonIgnore
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetails;

	@ManyToOne
	@JoinColumn(name = "username")
	private User username;
}
