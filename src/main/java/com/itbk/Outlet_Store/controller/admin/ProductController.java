package com.itbk.Outlet_Store.controller.admin;

import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.Product;
import com.itbk.Outlet_Store.domain.ResponseObject;
import com.itbk.Outlet_Store.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/products")
public class ProductController {

  private final ProductService productService;

  public ProductController ( ProductService productService){
    this.productService = productService;
  }

  @GetMapping("")
  public ResponseEntity<PageResult<Product>> getProducts(@RequestParam(value ="page",defaultValue = "0") int page,
                                                         @RequestParam(value = "size" ,defaultValue = "5") int size,
                                                         @RequestParam(value = "keyword", required = false) String keyword) {
    PageResult<Product> products = this.productService.getPageProducts(page, size, keyword);
    return ResponseEntity.ok(products);
  }

  @GetMapping("/list")
  public ResponseEntity<ResponseObject> getAllProduct(){
    List<Product> products = this.productService.getALlProduct();
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","Danh sach san pham",products ));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseObject> getProductById(@PathVariable long id) {
    Product product = this.productService.getProductById(id);
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "lấy thành công", product));
  }

  @PostMapping("")
  public ResponseEntity<ResponseObject> createProduct(
          @RequestParam("file") MultipartFile file,@ModelAttribute  Product product ,long categoryId) {
    Product savedProduct = this.productService.createProduct(product ,file ,categoryId);
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "thêm thành công", savedProduct));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ResponseObject> updateProduct(@PathVariable("id") Long id,
                                                      @RequestParam(name = "file", required = false) MultipartFile file,
                                                      @ModelAttribute Product product,
                                                      @RequestParam(name = "categoryId", required = false) Long categoryId){
    Product savedProduct = this.productService.updateProduct(product ,file, categoryId ,id);
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "cập nhật thành công", savedProduct));
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<ResponseObject> deleteProduct(@PathVariable long id) {
    Boolean isProduct = this.productService.deleteProduct(id);
    if (isProduct) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "delete product thành công", ""));
    }
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Không tìm thấy product", ""));

  }


}
