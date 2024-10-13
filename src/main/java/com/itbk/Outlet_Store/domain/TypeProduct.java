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
@Table(name = "TypeProduct")
public class TypeProduct implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, columnDefinition = "nvarchar(100) not null")
	private String name;

	@Column(length = 400)
	private String image;

	@JsonIgnore
	@OneToMany(mappedBy = "idTypeProduct", cascade = CascadeType.ALL)
	private Set<Category> categorys;
}
