package com.api.apiBackEnd.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.api.apiBackEnd.model.Product;
import com.api.apiBackEnd.shared.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product findProductById(int id){
        return productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Product with id "+id+" not found"));
    }

    public void deleteProductById(int id){
        if(!productRepository.existsById(id)){
            throw new NoSuchElementException("Product with id "+id+" not found");
        }
        productRepository.deleteById(id);
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(int id, Map<String,Object> updates){
        Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Product with id "+id+" not found"));
        updates.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Product.class,key);
            if(field  != null){
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingProduct,value);
            }
        });
        return productRepository.save(existingProduct);
    }

}
