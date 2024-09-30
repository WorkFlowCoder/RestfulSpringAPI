package com.api.apiBackEnd.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Product updateProduct(Product product, int id){
        Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Product with id "+id+" not found"));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setImage(product.getImage());
        existingProduct.setCategory(product.getCategory());
        long timestamp = new Date().getTime();
        existingProduct.setUpdatedAt(timestamp);
        return productRepository.save(existingProduct);
    }

}
