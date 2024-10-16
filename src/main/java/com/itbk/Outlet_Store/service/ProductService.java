package com.itbk.Outlet_Store.service;

import com.itbk.Outlet_Store.domain.Category;
import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.Product;
import com.itbk.Outlet_Store.domain.TypeProduct;
import com.itbk.Outlet_Store.repository.CategoryRepository;
import com.itbk.Outlet_Store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private  final CategoryService categoryService;
  private  final ImageService imageService;
  private final CategoryRepository categoryRepository;

  public ProductService(ProductRepository productRepository,CategoryService categoryService,
                        ImageService imageService,CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryService = categoryService;
    this.imageService = imageService;
    this.categoryRepository =categoryRepository;
  }

  public List<Product> getALlProduct(){
     return this.productRepository.findAll();
  }

  public Product getProductById(Long id){
    Optional<Product> product  = this.productRepository.findById(id);
    if(product.isPresent()){
       return product.get();
    }
    return  null;
  }

  public void deleteProduct(Long id){
    this.productRepository.deleteById(id);
  }

  public Product createProduct(Product product ,MultipartFile file){

    String generatedFileName = imageService.storageFile(file);

    Category categoryName = this.categoryService.getCategoryByName(product.getCategory().getName());

    product.setImage(generatedFileName);
    product.setCategory(categoryName);
    product.setEnteredDate(new Date());

    return this.productRepository.save(product);

  }

  public PageResult<Product> getPageProducts(int page, int size, String keyword){

    Pageable pageable = PageRequest.of(page, size);
    Page<Product> products;
    if (keyword != null && !keyword.isEmpty()) {
      products = productRepository.findByNameContaining(keyword, pageable);
    } else {
      products = productRepository.findAll(pageable);
    }
    return new PageResult<>(products.getContent(), products.getTotalPages());
  }


  public Product updateProduct(Product product ,MultipartFile file,long categoryId){

    String generatedFileName = imageService.storageFile(file);
    Category category = this.categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    product.setCategory(category);
    product.setImage(generatedFileName);
    product.setEnteredDate(new Date());
    return this.productRepository.save(product);

  }

  public boolean deleteProduct(long id){
    Product currentProduct = getProductById(id);
    if(currentProduct!= null) {
      this.productRepository.deleteById(id);
      return true;
    }
    return false;
  }



  public PageResult<Product> getProductsByTypeProduct(TypeProduct typeProduct, int page, int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Product> products;
    products = productRepository.findByCategory_IdTypeProduct(typeProduct, pageable);
    return new PageResult<>(products.getContent(), products.getTotalPages());
  }

  public PageResult<Product> getProductsByCategory(Long categoryId, int page, int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Product> products;
    products = productRepository.findByCategory_CategoryId(categoryId, pageable);

    return new PageResult<>(products.getContent(), products.getTotalPages());
  }


}
