package com.example.willhabenfake.Services;

import com.example.willhabenfake.Models.Product;
import com.example.willhabenfake.Models.User;
import com.example.willhabenfake.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    ProductRepository productRepository;

    UserService userService;

    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }


    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductsOfUser(Long userId) {
        Optional<User> user = userService.findUserById(userId);
        return user.map(_user -> productRepository.findAllByUser(_user)).orElse(null);

    }

    public boolean deleteProduct(Long productId) {
        try {
            productRepository.deleteById(productId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
