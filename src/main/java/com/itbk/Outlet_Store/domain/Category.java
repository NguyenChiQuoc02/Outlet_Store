package com.itbk.Outlet_Store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Categories")
public class Category implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;

	@Column(name = "category_name", length = 100, columnDefinition = "nvarchar(100) not null")
	private String name;

	// cascade = CascadeType.ALL được sử dụng để chỉ định rằng tất cả các thao tác
	// CRUD trên entity cha (One-side)
	// sẽ được áp dụng trên tất cả các instance của entity con (Many-side) tương ứng

	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Product> products;

	@ManyToOne()
	@JoinColumn(name = "idTypeProduct")
	private TypeProduct idTypeProduct;
}
