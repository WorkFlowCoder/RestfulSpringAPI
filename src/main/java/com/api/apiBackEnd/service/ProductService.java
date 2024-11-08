package com.api.apiBackEnd.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.api.apiBackEnd.dto.ProductDTO;
import com.api.apiBackEnd.entity.Product;
import com.api.apiBackEnd.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<ProductDTO> getAllProducts(){
        logger.info("Request to retrieve all products.");
        List<Product> products = productRepository.findAll();
        logger.info("Number of products retrieved: {}",products.size());
        return convertToDTOList((products));
    }

    public ProductDTO findProductById(Long id){
        logger.info("Request to retrieve product with ID : {}",id);
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Product with id "+id+" not found"));
        return convertToDTO(product);
    }

    public void deleteProductById(Long id){
        logger.info("Request to delete product with ID : {}",id);
        if(!productRepository.existsById(id)){
            throw new NoSuchElementException("Product with id "+id+" not found");
        }
        productRepository.deleteById(id);
        logger.info("Product deleted successfully with ID : {}",id);
    }

    public ProductDTO createProduct(Product product){
        logger.info("Request to create a new product : {}",product.getName());
        Product createProduct = productRepository.save(product);
        logger.info("Product created successfully with ID = {}",createProduct.getId());
        return convertToDTO(createProduct);
    }

    public ProductDTO updateProduct(Long id, Map<String,Object> updates){
        logger.info("Request to update product with ID : {}",id);
        Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Product with id "+id+" not found"));
        updates.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Product.class,key);
            if(field  != null){
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingProduct,value);
            }
        });
        existingProduct.setUpdatedAt(System.currentTimeMillis());
        Product updateProduct = productRepository.save(existingProduct);
        logger.info("Product updated successfully with ID = {}",id);
        return convertToDTO(updateProduct);
    }

    public ProductDTO convertToDTO(Product product){
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getImage(),
            product.getCategory(),
            product.getPrice(),
            product.getQuantity(),
            product.getRating()
        );
    }

    public List<ProductDTO> convertToDTOList(List<Product> products){
        List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
        for (Product product : products){
            productsDTO.add(convertToDTO(product));
        }
        return productsDTO;
    }

}
