package org.productservice.service;



import org.productservice.dto.ProductRequest;
import org.productservice.dto.ProductResponse;
import org.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    Page<ProductResponse> findAll(Pageable pageable);

    void delete(String id);

    String uploadThumbnail(MultipartFile file, String id);

    Product getProductById(String id);
}
