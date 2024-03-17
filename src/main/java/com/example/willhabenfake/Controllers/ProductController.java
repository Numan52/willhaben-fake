package com.example.willhabenfake.Controllers;

import com.example.willhabenfake.Models.Image;
import com.example.willhabenfake.Models.ProductDto;
import com.example.willhabenfake.Models.Product;
import com.example.willhabenfake.Services.ProductService;
import com.example.willhabenfake.Models.User;
import com.example.willhabenfake.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private ProductService productService;
    private UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }


    @Transactional
    @PostMapping("/products/create")
    public String createAd(@RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("price") double price,
                           @RequestParam("category") String category,
                           @RequestParam("images") MultipartFile[] images,
                           @CookieValue(name = "userEmail", defaultValue = "unknown") String userEmail) {

        List<Image> productImages = new ArrayList<>();
        for (MultipartFile productImage : images) {
            if (!productImage.isEmpty()) {
                try {
                    productImages.add(new Image(productImage.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        User user = userService.findUserByEmail(userEmail);
        Product product = new Product(title, category, price, description, productImages, user);

        System.out.println(product);
        productService.createProduct(product);

        return "redirect:/";
    }

    @Transactional
    @GetMapping("/products/product-details")
    public ResponseEntity<ProductDto> getProductDetails(@RequestParam("id") Long productId) {
        Optional<Product> productOptional = productService.getProduct(productId);
        System.out.println(productOptional.isPresent());
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(ProductDto.toProductDto(productOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Transactional
    @GetMapping("/products/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(ProductDto.toProductDtoList(products), HttpStatus.OK);
    }

    @GetMapping("/products/get-user-products")
    public ResponseEntity<?> getAllUserProducts(@RequestParam("userId") Long userId) {
        List<Product> products = productService.getAllProductsOfUser(userId);
        if (products != null) {
            return ResponseEntity.ok(ProductDto.toProductDtoList(products));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/products/delete-product")
    public ResponseEntity<?> deleteProduct(@RequestParam("productId") Long productId) {
        boolean successful = productService.deleteProduct(productId);
        if (successful) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.internalServerError().body("Error occurred");
        }
    }
}
