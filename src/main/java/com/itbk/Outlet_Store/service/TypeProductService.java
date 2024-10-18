package com.itbk.Outlet_Store.service;

import com.itbk.Outlet_Store.domain.Category;
import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.Product;
import com.itbk.Outlet_Store.domain.TypeProduct;
import com.itbk.Outlet_Store.repository.TypeProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class TypeProductService {

  private final TypeProductRepository typeProductRepository;
  private final ProductService productService;
  private final ImageService imageService;

  public TypeProductService(TypeProductRepository typeProductRepository,ProductService productService, ImageService imageService) {
    this.typeProductRepository = typeProductRepository;
    this.productService = productService;
    this.imageService = imageService;
  }

  public PageResult<Product> getTypeProduct( int page,int size ,long id){
    TypeProduct typeProduct = this.typeProductRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("TypeProduct not found with id: " + id));
    PageResult<Product> products = this.productService.getProductsByTypeProduct(typeProduct, page, size);
    return products;
  }

  public TypeProduct findByidTypeProduct_Id(Long id){
    Optional<TypeProduct> typeProduct = this.typeProductRepository.findById(id);
    if(typeProduct.isPresent()){
      return typeProduct.get();
    }
     return null;
  }

   public List<TypeProduct> getAllTypeProdct(){
    return this.typeProductRepository.findAll();
   }
  public PageResult<TypeProduct> getTypeProducts(int page, int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<TypeProduct> typeProduct;
    typeProduct = typeProductRepository.findAll(pageable);
    return new PageResult<>(typeProduct.getContent(), typeProduct.getTotalPages());
  }

  public TypeProduct saveTypeProduct(String name ,  MultipartFile file){
    String generatedFileName = this.imageService.storageFile(file);
    TypeProduct typeProduct = new TypeProduct();
    typeProduct.setName(name);
    typeProduct.setImage(generatedFileName);
    TypeProduct saveTypeProduct = typeProductRepository.save(typeProduct);
    return typeProduct;
  }

  public TypeProduct updateTypeProduct(long id , String name , MultipartFile file) {
    TypeProduct existingTypeProduct = typeProductRepository.findById(id).orElse(null);
    if (existingTypeProduct == null) {
      return null;
    }
    if (file != null) {
      String generatedFileName = this.imageService.storageFile(file);
      existingTypeProduct.setImage(generatedFileName);
    }
    if (name != null) {
      existingTypeProduct.setName(name);
    }

    // Cập nhật thông tin của danh mục
    TypeProduct updatedTypeProduct = typeProductRepository.save(existingTypeProduct);
    return existingTypeProduct;
  }



  public boolean deleteTypeProduct(long id){
    Optional<TypeProduct> opt = typeProductRepository.findById(id);
    if (opt.isPresent()) {
      TypeProduct TypeProduct = opt.get();
      this.typeProductRepository.delete(TypeProduct);
      return true;
    }
    return false;
  }
}

