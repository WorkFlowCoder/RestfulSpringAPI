package com.api.apiBackEnd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name="product")
public class Product{

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message="Product name is required")
    @Column(name="name", insertable=true, updatable=true, nullable=false)
    private String name;

    @NotBlank(message="Description is required")
    @Column(name="description", insertable=true, updatable=true, nullable=false)
    private String description;

    @Column(name="image", insertable=true, updatable=true, nullable=true)
    private String image;

    @NotBlank(message="Category is required")
    @Column(name="category", insertable=true, updatable=true, nullable=false)
    private String category;

    @Positive(message="Price must be positive")
    @Column(name="price", insertable=true, updatable=true, nullable=false)
    private double price;

    @PositiveOrZero(message="Quantity must be zero or greater")
    @Column(name="quantity", insertable=true, updatable=true, nullable=false)
    private int quantity;

    @Column(name="internalReference", insertable=true, updatable=true, nullable=false)    
    private String internalReference;

    @Column(name="shellId", insertable=true, updatable=true, nullable=false)
    private int shellId;

    @NotBlank(message="Inventory status is required")
    @Column(name="inventoryStatus", insertable=true, updatable=true, nullable=false)
    private String inventoryStatus;

    @Min(value=0,message="Rating must be at least 0")
    @Max(value=5,message="Rating cannot be greater than 5")
    @Column(name="rating", insertable=true, updatable=true, nullable=false)
    private int rating;

    @Column(name="code", insertable=true, updatable = true, nullable=false)
    private String code;

    @Column(name="createdAt", insertable=true, updatable=true, nullable=false)
    private Long createdAt = System.currentTimeMillis();

    @Column(name="updatedAt", insertable=true, updatable=true, nullable=false)
    private Long updatedAt = System.currentTimeMillis();

    public Product(){}

    public Product(Long id, String name, String description, String image,String category, double price, int quantity, int rating,
        String code, int shellId, String internalReference, String inventoryStatus){
        this.id = id;
        this.name=name;
        this.description=description;
        this.image=image;
        this.category=category;
        this.price=price;
        this.quantity=quantity;
        this.internalReference=internalReference;
        this.shellId=shellId;
        this.code=code;
        this.rating=rating;
        this.inventoryStatus=inventoryStatus;
    }

    public Product(String name, String description, String image,String category, double price, int quantity, int rating,
        String code, int shellId, String internalReference, String inventoryStatus){
        this.name=name;
        this.description=description;
        this.image=image;
        this.category=category;
        this.price=price;
        this.quantity=quantity;
        this.internalReference=internalReference;
        this.shellId=shellId;
        this.code=code;
        this.rating=rating;
        this.inventoryStatus=inventoryStatus;
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


    public String getInternalReference(){
        return this.internalReference;
    }

    public void setInternalReference(String internalReference){
        this.internalReference = internalReference;
    }


    public int getShellId(){
        return this.shellId;
    }

    public void setShellId(int shellId){
        this.shellId = shellId;
    }


    public String getInventoryStatus(){
        return this.inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus){
        this.inventoryStatus = inventoryStatus;
    }

    public int getRating(){
        return this.rating;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public String getCode(){
        return this.code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public Long getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(Long createdAt){
        this.createdAt = createdAt;
    }

    public Long getupdatedAt(){
        return this.updatedAt;
    }

    public void setUpdatedAt(Long updatedAt){
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString(){
        return "Product [id="+this.id+", name="+this.name
                + ", description="+this.description+", image="+this.image
                +", category="+this.category+", price="+this.price
                +", quantity="+this.quantity+", rating="+this.rating
                +", code="+this.code+", shellId="+this.shellId
                +", createdAt="+this.createdAt+", updatedAt="+this.updatedAt
                +", internalReference="+this.inventoryStatus+", shellId="+this.inventoryStatus
                +"]";
    }

}
