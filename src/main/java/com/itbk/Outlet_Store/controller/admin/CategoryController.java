package com.itbk.Outlet_Store.controller.admin;

import com.itbk.Outlet_Store.domain.Category;
import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.ResponseObject;
import com.itbk.Outlet_Store.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("admin/category")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("")
  public ResponseEntity<PageResult<Category>> getCategorys(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size,
                                                           @RequestParam(value = "keyword", required = false) String keyword) {
    PageResult<Category> Categorys = this.categoryService.getCategorys(page, size, keyword);
    return ResponseEntity.ok(Categorys);
  }

  @GetMapping("list")
  public List<Category> getAllProducts() {
    return this.categoryService.getAllCategory();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
    return ResponseEntity.ok(this.categoryService.getCategoryById(id));
  }

  @PostMapping("")
  public ResponseEntity<ResponseObject> createCategory(@RequestBody Category category) {
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Thêm thành công ", this.categoryService.createCategory(category)));
  }



}
