package com.api.apiBackEnd.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private int rating;

    public ProductDTO(){}

    public ProductDTO(Long id, String name, String description, String image,String category, double price, int quantity, int rating){
        this.id = id;
        this.name=name;
        this.description=description;
        this.image=image;
        this.category=category;
        this.price=price;
        this.quantity=quantity;
        this.rating=rating;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getCategory(){
        return this.category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getRating(){
        return this.rating;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    @Override
    public String toString(){
        return "Product [name="+this.name
                + ", description="+this.description+", image="+this.image
                +", category="+this.category+", price="+this.price
                +", quantity="+this.quantity+", rating="+this.rating
                +"]";
    }
}
