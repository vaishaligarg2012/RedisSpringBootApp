package com.ensat.services;

import com.ensat.entities.Product;
import com.ensat.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Product service implement.
 */
@Service
public class ProductServiceImpl implements ProductService {
     @Autowired
     private ProductRepository productRepository;



    @Override
    @Cacheable(value = "products", key = "'allProducts'")
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).get();
    }

    @Override
            //@CachePut(value = "products", key = "'allProducts'")
    public Product saveProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        updateCachedProductList(product);
       return savedProduct;
       //return productRepository.findAll();
        // return productRepository.findAll();
    }
    /*
       @Override
    @CachePut(value = "products", key = "'allProducts'")
    public List<Product> saveProduct(Product product) {
      //  updateCachedProductList(product);
        productRepository.save(product);
        return productRepository.findAll();
        // return productRepository.findAll();
    }
     */

    // Helper method to update the cached list when a product is saved/updated
   // @CachePut(value = "products", key = "'allProducts'")
    private void updateCachedProductList(Product product) {
        List<Product> cachedProducts = listAllProducts();
        // Find the product with the same ID as the saved/updated product
        for (int i = 0; i < cachedProducts.size(); i++) {
            if (cachedProducts.get(i).getId().equals(product.getId())) {
                // Update the product in the cached list
                cachedProducts.set(i, product);
                break;
            }
        }
    }


    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

}
