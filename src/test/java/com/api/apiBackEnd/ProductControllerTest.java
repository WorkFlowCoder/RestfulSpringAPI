package com.api.apiBackEnd;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.mockito.MockitoAnnotations;


import com.api.apiBackEnd.controller.ProductController;
import com.api.apiBackEnd.entity.Product;
import com.api.apiBackEnd.dto.ProductDTO;
import com.api.apiBackEnd.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProduct() throws Exception{
        //List of products
        ProductDTO p1 = new ProductDTO(1L,"Smartphone X", "Latest smartphone with a 6.5-inch display, 128GB storage, and powerful battery life.", "smartphone_x.png", "Electronics", 699.99, 50, 4);
        ProductDTO p2 = new ProductDTO(2L,"Office Chair", "Ergonomic office chair designed for comfort and lumbar support.", "office_chair.png", "Furniture", 149.99, 41, 5);
        List<ProductDTO> allProducts = Arrays.asList(p1,p2);
        given(productService.getAllProducts()).willReturn(allProducts);
        mockMvc.perform(get("/products"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name", is("Smartphone X")))
        .andExpect(jsonPath("$[1].name", is("Office Chair")));
    }

    @Test
    public void testFindProductById() throws Exception{
        ProductDTO p1 = new ProductDTO(1L,"Smartphone X", "Latest smartphone with a 6.5-inch display, 128GB storage, and powerful battery life.", "smartphone_x.png", "Electronics", 699.99, 50, 4);
        given(productService.findProductById(1L)).willReturn(p1);
        mockMvc.perform(get("/products/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.name", is("Smartphone X")))
        .andExpect(jsonPath("$.price", is(699.99)));
    }

    @Test
    public void testFindProductByIdNotFound() throws Exception{
        Long id=99L;
        doThrow(new NoSuchElementException("Product with id "+id+" not found"))
        .when(productService).findProductById(id);
        mockMvc.perform(get("/products/"+id))
        .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProductById() throws Exception{
        Mockito.doNothing().when(productService).deleteProductById(1L);
        mockMvc.perform(delete("/products/1"))
        .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteProductByIdNotFound()throws Exception{
        Long id=99L;
        doThrow(new NoSuchElementException("Product with id "+id+" not found"))
        .when(productService).deleteProductById(id);
        mockMvc.perform(delete("/products/"+id))
        .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct() throws Exception{
        Product p = new Product("Smartphone X", "Updated smartphone vision", "smartphone_X.png", "Electronics", 799.99, 40, 5, "CODE_1", 1, "PRODUCT_1", "INSTOCK");
        ProductDTO pdto = new ProductDTO(1L,"Smartphone X", "Updated smartphone vision", "smartphone_X.png", "Electronics", 799.99, 40, 5);

        when(productService.createProduct(Mockito.any(Product.class))).thenReturn(pdto);

        mockMvc.perform(post("/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(p)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name",is("Smartphone X")));
    }

    @Test void testCreateProductBadRequest() throws Exception {
        Product invalidProduct = new Product(); 
        invalidProduct.setPrice(799.99);
        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(invalidProduct)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Long id = 1L;
        Product updateProduct = new Product();
        updateProduct.setName("Product Test");
        Map<String, Object> updates = new HashMap<>();
        updates.put("name","new name");

        ProductDTO updatedProduct = new ProductDTO();
        updatedProduct.setId(id);
        updatedProduct.setName("new name");
        when(productService.updateProduct(Mockito.eq(id), ArgumentMatchers.<Map<String, Object>>any())).thenReturn(updatedProduct);
        mockMvc.perform(patch("/products/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"new name\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("new name")));
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        Long id = 1L;
        when(productService.updateProduct(Mockito.eq(id), ArgumentMatchers.<Map<String, Object>>any())).thenThrow(new NoSuchElementException("Product with id 1 not found"));
        mockMvc.perform(patch("/products/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"new name\"}"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", is("Product with id 1 not found")));
    }

}
