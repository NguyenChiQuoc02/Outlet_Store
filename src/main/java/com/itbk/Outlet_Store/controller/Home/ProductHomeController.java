package com.itbk.Outlet_Store.controller.Home;

import com.itbk.Outlet_Store.domain.*;
import com.itbk.Outlet_Store.service.CategoryService;
import com.itbk.Outlet_Store.service.ProductService;
import com.itbk.Outlet_Store.service.TypeProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/home")
public class ProductHomeController {

  private final ProductService productService;
  private final CategoryService categoryService;
  private final TypeProductService typeProductService;

  public ProductHomeController ( ProductService productService,
                                 CategoryService categoryService,
                                 TypeProductService typeProductService
  ){
    this.productService = productService;
    this.categoryService = categoryService;
    this.typeProductService = typeProductService;
  }

  @GetMapping("category/list")
  public List<Category> getAllCategory() {
    return this.categoryService.getAllCategory();
  }
  @GetMapping("typeproduct/list")
  public List<TypeProduct> getAllTypeProduct() {
    return this.typeProductService.getAllTypeProdct();
  }

  @GetMapping("typeproduct/{id}")
  public TypeProduct getCategoriesByTypeProduct(@PathVariable Long id) {
    return this.typeProductService.findByidTypeProduct_Id(id);
  }

  @GetMapping("product/list")
  public List<Product> getAllProducts() {
    return this.productService.getALlProduct();
  }

  @GetMapping("product/detail/{id}")
  public ResponseEntity<ResponseObject> getProductById(@PathVariable long id) {
    Product product = this.productService.getProductById(id);
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "lấy thành công", product));
  }

  @GetMapping("product/typeproduct")
  public ResponseEntity<PageResult<Product>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "4") int size,
                                                         @RequestParam("typeProductId") Long typeProductId) {
    PageResult<Product> products = this.typeProductService.getTypeProduct(page , size, typeProductId);
    return ResponseEntity.ok(products);
  }

  @GetMapping("product/category")
  public ResponseEntity<PageResult<Product>> getProductsByCategory(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "4") int size,
                                                                   @RequestParam("categoryId") Long categoryId) {

    PageResult<Product> products = this.productService.getProductsByCategory(categoryId, page, size);
    return ResponseEntity.ok(products);
  }

}
