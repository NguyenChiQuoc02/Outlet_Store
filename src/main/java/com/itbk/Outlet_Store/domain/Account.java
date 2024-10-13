package com.itbk.Outlet_Store.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
// implements Serializable chuyển đổi thành một chuỗi byte để lưu trữ hoặc
// truyền qua mạng
public class Account implements Serializable {

	@Id
	@Column(length = 30)
	private String username;

	@Column(length = 100, nullable = false)
	private String password;

	@Column(columnDefinition = "nvarchar(100)")
	private String fullname;

	@Column(length = 100)
	private String email;

	private boolean admin;

	// CascadeType.ALL Khi một thay đổi trạng thái xảy ra trên đối tượng Parent (ví
	// dụ: khi nó được xóa),
	// tất cả các đối tượng con của nó (được đặt trong danh sách children) sẽ cũng
	// bị xóa.
	// @JsonIgnore
	// @OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
	// private Set<Order> orders;
}