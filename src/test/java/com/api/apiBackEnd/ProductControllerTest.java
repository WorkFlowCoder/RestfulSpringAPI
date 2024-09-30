package com.api.apiBackEnd;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.api.apiBackEnd.controller.ProductController;
import com.api.apiBackEnd.model.Product;
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
        Product p1 = new Product(1,"Smartphone X", "Latest smartphone with a 6.5-inch display, 128GB storage, and powerful battery life.", "smartphone_x.png", "Electronics", 699.99, 50, 4, "CODE_1", 1, "PRODUCT_1", "INSTOCK");
        Product p2 = new Product(2,"Office Chair", "Ergonomic office chair designed for comfort and lumbar support.", "office_chair.png", "Furniture", 149.99, 41, 5, "CODE_2", 2, "PRODUCT_2", "LOWSTOCK");
        List<Product> allProducts = Arrays.asList(p1,p2);
        given(productService.getAllProducts()).willReturn(allProducts);
        mockMvc.perform(get("/products"))
        .andExpect(status().isOk())  // Vérifie que le statut HTTP est 200 OK
        .andExpect(content().contentType("application/json"))  // Vérifie que la réponse est du JSON
        .andExpect(jsonPath("$", hasSize(2)))  // Vérifie qu'il y a 2 éléments dans la réponse
        .andExpect(jsonPath("$[0].name", is("Smartphone X")))
        .andExpect(jsonPath("$[1].name", is("Office Chair")));
    }

    @Test
    public void testFindProductById() throws Exception{
        Product p1 = new Product(1,"Smartphone X", "Latest smartphone with a 6.5-inch display, 128GB storage, and powerful battery life.", "smartphone_x.png", "Electronics", 699.99, 50, 4, "CODE_1", 1, "PRODUCT_1", "INSTOCK");
        given(productService.findProductById(1)).willReturn(p1);
        mockMvc.perform(get("/products/1"))
        .andExpect(status().isOk())  // Vérifie que le statut HTTP est 200 OK
        .andExpect(content().contentType("application/json"))  // Vérifie que la réponse est du JSON
        .andExpect(jsonPath("$.id", is(1)))  // Vérifie qu'il y a 2 éléments dans la réponse
        .andExpect(jsonPath("$.name", is("Smartphone X")))
        .andExpect(jsonPath("$.price", is(699.99)));
    }

    @Test
    public void testFindProductByIdNotFound() throws Exception{
        int id=99;
        doThrow(new NoSuchElementException("Product with id "+id+" not found"))
        .when(productService).findProductById(id);
        mockMvc.perform(get("/products/"+id))
        .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProductById() throws Exception{
        Mockito.doNothing().when(productService).deleteProductById(1);
        mockMvc.perform(delete("/products/1"))
        .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteProductByIdNotFound()throws Exception{
        int id=99;
        doThrow(new NoSuchElementException("Product with id "+id+" not found"))
        .when(productService).deleteProductById(id);
        mockMvc.perform(delete("/products/"+id))
        .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct() throws Exception{
        Product p = new Product("Smartphone X", "Updated smartphone vision", "smartphone_X.png", "Electronics", 799.99, 40, 5, "CODE_1", 1, "PRODUCT_1", "INSTOCK");
        when(productService.createProduct(Mockito.any(Product.class))).thenReturn(p);
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
        Product updatedProduct = new Product(1, "Smartphone Z", "Updated smartphone", "smartphone_y.png", "Electronics", 799.99, 40, 5, "CODE_1", 1, "PRODUCT_1", "INSTOCK");
        when(productService.updateProduct(Mockito.any(Product.class), Mockito.eq(1))).thenReturn(updatedProduct);
        mockMvc.perform(put("/products/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updatedProduct)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Smartphone Z")));
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        Product updatedProduct = new Product(1, "Smartphone Z", "Updated smartphone", "smartphone_y.png", "Electronics", 799.99, 40, 5, "CODE_1", 1, "PRODUCT_1", "INSTOCK");
        when(productService.updateProduct(Mockito.any(Product.class), Mockito.eq(1))).thenThrow(new NoSuchElementException("Product with id 1 not found"));
        mockMvc.perform(put("/products/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updatedProduct)))
        .andExpect(status().isNotFound())  // Vérifier le code d'erreur 404
        .andExpect(jsonPath("$.message", is("Product with id 1 not found")));  // Vérifier le message d'erreur
    }

    @Test void testUpdateProductBadRequest() throws Exception {
        Product invalidProduct = new Product(); 
        mockMvc.perform(put("/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(invalidProduct)))
            .andExpect(status().isBadRequest());
    }
}
