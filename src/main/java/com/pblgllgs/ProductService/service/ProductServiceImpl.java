package com.pblgllgs.ProductService.service;

import com.pblgllgs.ProductService.entity.Product;
import com.pblgllgs.ProductService.exception.ProductServiceCustomException;
import com.pblgllgs.ProductService.model.ProductRequest;
import com.pblgllgs.ProductService.model.ProductResponse;
import com.pblgllgs.ProductService.repositoty.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("adding product...");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for the productId: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("product with id not found", "PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity {} for id: {}",quantity, productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException(
                        "Product with id not found",
                        "PRODUCT_NOT_FOUNT"
                ));
        if (product.getQuantity() < quantity){
            throw  new ProductServiceCustomException(
                    "Product does not have sufficient quantity",
                    "INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity Updated Successfully");
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> {
            ProductResponse productResponse = new ProductResponse();
            BeanUtils.copyProperties(product, productResponse);
            return productResponse;
        }).collect(Collectors.toList());
    }
}
