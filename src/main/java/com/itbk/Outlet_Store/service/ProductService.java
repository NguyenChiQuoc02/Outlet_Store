package com.itbk.Outlet_Store.service;

import com.itbk.Outlet_Store.domain.Category;
import com.itbk.Outlet_Store.domain.PageResult;
import com.itbk.Outlet_Store.domain.Product;
import com.itbk.Outlet_Store.domain.TypeProduct;
import com.itbk.Outlet_Store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private  final CategoryService categoryService;
  private  final ImageService imageService;

  public ProductService(ProductRepository productRepository,CategoryService categoryService,ImageService imageService) {
    this.productRepository = productRepository;
    this.categoryService = categoryService;
    this.imageService = imageService;
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

    Product currentProduct = new Product();

    Category categoryName = this.categoryService.getCategoryByName(product.getCategory().getName());
    currentProduct.setDescription(product.getDescription());
    currentProduct.setDiscount(product.getDiscount());
    currentProduct.setEnteredDate(product.getEnteredDate());
    currentProduct.setImage(generatedFileName);
    currentProduct.setName(product.getName());
    currentProduct.setQuantity(product.getQuantity());
    currentProduct.setUnitPrice(product.getUnitPrice());
    currentProduct.setXuatXu(product.getXuatXu());
    currentProduct.setCategory(categoryName);

    return this.productRepository.save(currentProduct);

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


  public Product updateProduct(Product product ,MultipartFile file,Long id){

    String generatedFileName = imageService.storageFile(file);

    Product currentProduct = getProductById(id);

    Category categoryName = this.categoryService.getCategoryByName(product.getCategory().getName());
    currentProduct.setDescription(product.getDescription());
    currentProduct.setDiscount(product.getDiscount());
    currentProduct.setEnteredDate(product.getEnteredDate());
    currentProduct.setImage(generatedFileName);
    currentProduct.setName(product.getName());
    currentProduct.setQuantity(product.getQuantity());
    currentProduct.setUnitPrice(product.getUnitPrice());
    currentProduct.setXuatXu(product.getXuatXu());
    currentProduct.setCategory(categoryName);

    return this.productRepository.save(currentProduct);

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
