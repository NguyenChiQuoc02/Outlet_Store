package com.itbk.Outlet_Store.service;

import com.itbk.Outlet_Store.domain.User;
import com.itbk.Outlet_Store.repository.AccountRepository;
import org.springframework.util.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

  private final PasswordEncoder passwordEncoder;
  private final AccountRepository accountRepository;


  public AccountService(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
    this.passwordEncoder = passwordEncoder;
    this.accountRepository = accountRepository;
  }

  public User save(User entity) {

    Optional<User> optExist = findById(entity.getId());

    if (optExist.isPresent()) {

      // Nếu mật khẩu của tài khoản mới là rỗng,
      // phương thức sẽ giữ nguyên mật khẩu cũ của tài khoản đã tồn tại.
      if (StringUtils.isEmpty(entity.getPassword())) {
        entity.setPassword(optExist.get().getPassword());
      } else {
        // Nếu mật khẩu của tài khoản mới không rỗng,
        // phương thức sẽ mã hóa mật khẩu mới bằng cách sử dụng bcryptPasswordEncoder
        // và cập nhật mật khẩu mới cho tài khoản.
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
      }
    } else {
      entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    }

    return accountRepository.save(entity);
  }


  public Optional<User> findById(Long id) {
    return accountRepository.findById(id);
  }

}
