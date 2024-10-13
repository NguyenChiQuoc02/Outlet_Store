package com.itbk.Outlet_Store.service;

import com.itbk.Outlet_Store.domain.Category;
import com.itbk.Outlet_Store.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category getCategoryById(Long id){
    Optional<Category> currentCategory = this.categoryRepository.findById(id);
    if(currentCategory.isPresent()) {
      return currentCategory.get();
    }
    return null;
  }

  public Category getCategoryByName(String name){
    return this.categoryRepository.findByName(name);
  }


}
