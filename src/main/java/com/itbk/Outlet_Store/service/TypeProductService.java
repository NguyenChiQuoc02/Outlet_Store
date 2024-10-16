package com.itbk.Outlet_Store.service;

import com.itbk.Outlet_Store.domain.Category;
import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.Product;
import com.itbk.Outlet_Store.domain.TypeProduct;
import com.itbk.Outlet_Store.repository.TypeProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeProductService {

  private final TypeProductRepository typeProductRepository;
  private final ProductService productService;

  public TypeProductService(TypeProductRepository typeProductRepository,ProductService productService) {
    this.typeProductRepository = typeProductRepository;
    this.productService = productService;
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
}

