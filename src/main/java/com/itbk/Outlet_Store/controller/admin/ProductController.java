package com.itbk.Outlet_Store.controller.admin;

import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.Product;
import com.itbk.Outlet_Store.domain.ResponseObject;
import com.itbk.Outlet_Store.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/listPage")
  public ResponseEntity<ResponseObject> getProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size,
                                                         @RequestParam(value = "keyword", required = false) String keyword) {
    PageResult<Product> products = this.productService.getPageProducts(page, size, keyword);
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","Danh sach san pham theo trang",products ));
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

  @PostMapping("/add")
  public ResponseEntity<ResponseObject> createProduct(
          @RequestParam("file") MultipartFile file,
          @RequestParam("name") String name, @RequestParam("quantity") int quantity,
          @RequestParam("unitPrice") int unitPrice, @RequestParam("description") String description,
          @RequestParam("discount") int discount, @RequestParam("xuatXu") String xuatXu,
          @RequestParam("categoryId") Long categoryId) {
    Product product = new Product();
    product.setName(name);
    product.setQuantity(quantity);
    product.setUnitPrice(unitPrice);
    product.setDescription(description);
    product.setDiscount(discount);
    product.setXuatXu(xuatXu);

    Product savedProduct = this.productService.createProduct(product ,file );
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "thêm thành công", savedProduct));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ResponseObject> updateProduct(@PathVariable("id") Long id,
                                                      @RequestParam(name = "file", required = false) MultipartFile file,
                                                      @RequestParam(name = "name", required = false) String name,
                                                      @RequestParam(name = "quantity", required = false) Integer quantity,
                                                      @RequestParam(name = "unitPrice", required = false) Integer unitPrice,
                                                      @RequestParam(name = "description", required = false) String description,
                                                      @RequestParam(name = "discount", required = false) Integer discount,
                                                      @RequestParam(name = "xuatXu", required = false) String xuatXu,
                                                      @RequestParam(name = "categoryId", required = false) Long categoryId){
    Product product = this.productService.getProductById(id);
    if (name != null) {
      product.setName(name);
    }

    if (quantity != null) {
      product.setQuantity(quantity);
    }

    if (unitPrice != null) {
      product.setUnitPrice(unitPrice);
    }

    if (description != null) {
      product.setDescription(description);
    }

    if (discount != null) {
      product.setDiscount(discount);
    }

    if (xuatXu != null) {
      product.setXuatXu(xuatXu);
    }
    Product savedProduct = this.productService.updateProduct(product ,file, categoryId);
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
