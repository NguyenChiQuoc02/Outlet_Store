package com.itbk.Outlet_Store.controller.admin;

import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.ResponseObject;
import com.itbk.Outlet_Store.domain.TypeProduct;
import com.itbk.Outlet_Store.service.ImageService;
import com.itbk.Outlet_Store.service.TypeProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("admin/typeproduct")
public class TypeProductController {

  private final TypeProductService typeProductService;
  private final ImageService imageService;

  public TypeProductController(TypeProductService typeProductService, ImageService imageService) {
    this.typeProductService = typeProductService;
    this.imageService = imageService;
  }

  @GetMapping("")
  public ResponseEntity<PageResult<TypeProduct>> getTypeProducts(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
    PageResult<TypeProduct> typeProducts = typeProductService.getTypeProducts(page, size);
    return ResponseEntity.ok(typeProducts);
  }

  @GetMapping("list")
  public List<TypeProduct> getAllProducts() {
    return this.typeProductService.getAllTypeProdct();
  }

  @GetMapping("/{id}")
  public ResponseEntity<TypeProduct> getTypeProductById(@PathVariable Long id) {
    return ResponseEntity.ok(this.typeProductService.findByidTypeProduct_Id(id));
  }

  @PostMapping("")
  public ResponseEntity<ResponseObject> createTypeProduct(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("name") String name) {
    TypeProduct typeProduct = this.typeProductService.saveTypeProduct(name,file);
    return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseObject("ok", "up thành công", typeProduct));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ResponseObject> updateProduct(
          @PathVariable Long id,
          @RequestParam(name = "file", required = false) MultipartFile file,
          @RequestParam(name = "name", required = false) String name) {
    TypeProduct updateTypeProduct = this.typeProductService.updateTypeProduct(id, name, file);
    return ResponseEntity
            .ok(new ResponseObject("OK", "Cập nhật thành công", updateTypeProduct));
  }

  @DeleteMapping("delete/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {

    boolean valdel = this.typeProductService.deleteTypeProduct(id);
    if (valdel) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "delete sản phẩm thành công", ""));
    }
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Không tìm thấy sản phẩm", ""));

  }
}
