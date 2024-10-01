package org.productservice.service;



import org.productservice.dto.ProductRequest;
import org.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> findAll();
}
