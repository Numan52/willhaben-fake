package com.example.willhabenfake.Models;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDto {
    private Long id;
    private String title;
    private String category;
    private double price;
    private String description;
    private List<ImageDto> images;
    private String username;

    public ProductDto() {}

    public ProductDto(Long id, String title, String category, double price, String description, List<ImageDto> images, String username) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.description = description;
        this.images = images;
        this.username = username;
    }

    public static ProductDto toProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setCategory(product.getCategory());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImages(ImageDto.toImageDtoList(product.getImages()));
        productDto.setUsername(product.getUser().getUsername());
        return productDto;
    }

    public static List<ProductDto> toProductDtoList (List<Product> products) {
        return products.stream().
                map(product -> toProductDto(product)).
                collect(Collectors.toList());
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
