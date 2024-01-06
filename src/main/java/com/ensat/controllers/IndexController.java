package com.ensat.controllers;

import com.ensat.entities.Product;
import com.ensat.repositories.ProductRepository;
import com.ensat.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    List<Product> index() {
        return  productService.listAllProducts();
    }

    @GetMapping("/calculate")
    @Cacheable(value = "calculate", key = "'calculate'")
    public String calculateResult(@RequestParam("input") String input) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Result for " + input;
    }

    @PostMapping("/add")
    public List<Product> addProduct(@RequestBody Product product){
        return (List<Product>) productService.saveProduct(product);
    }

    @GetMapping("/clear_cache")
    @CacheEvict(value = "products", allEntries = true )
    public String clearCache(){
        return "Cache has been cleared";
    }

}
