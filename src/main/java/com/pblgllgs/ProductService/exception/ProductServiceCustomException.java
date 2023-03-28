package com.pblgllgs.ProductService.exception;

import lombok.Data;

@Data
public class ProductServiceCustomException extends RuntimeException{

    private String errorCode;

    public ProductServiceCustomException(String message, String codeError){
        super(message);
        this.errorCode = codeError;
    }
}
