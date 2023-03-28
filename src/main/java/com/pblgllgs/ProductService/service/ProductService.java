package com.pblgllgs.ProductService.service;

import com.pblgllgs.ProductService.entity.Product;
import com.pblgllgs.ProductService.model.ProductRequest;
import com.pblgllgs.ProductService.model.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);

    List<ProductResponse> getAllProducts();
}
