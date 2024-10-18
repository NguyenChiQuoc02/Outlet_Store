package com.itbk.Outlet_Store.controller.admin;

import com.itbk.Outlet_Store.domain.Category;
import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.ResponseObject;
import com.itbk.Outlet_Store.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
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
  public ResponseEntity<ResponseObject> getAllProducts() {
    List<Category> categories = this.categoryService.getAllCategory();
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Thêm thành công ", categories));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
    return ResponseEntity.ok(this.categoryService.getCategoryById(id));
  }

  @PostMapping("")
  public ResponseEntity<ResponseObject> createCategory(@RequestBody Category category) {
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Thêm thành công ", this.categoryService.createCategory(category)));
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<ResponseObject> updateProduct(
          @PathVariable Long id, @RequestParam("name") String name,
          @RequestParam("idTypeProduct") Long idTypeProduct) {
    try {
      Category updatedCategory = this.categoryService.updateCategory(id, name, idTypeProduct);
      return ResponseEntity.ok(new ResponseObject("success", "Category updated successfully", updatedCategory));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ResponseObject("error", "Failed to update product: " + e.getMessage(), null));
    }
  }
  @DeleteMapping("delete/{id}")
  public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
      boolean valdel = this.categoryService.deleteCategory(id);
      if(valdel) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "delete product Thành công", ""));
      }
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Không tìm thấy product", ""));

  }

}
