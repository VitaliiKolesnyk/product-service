package org.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.productservice.dto.ProductRequest;
import org.productservice.dto.ProductResponse;
import org.productservice.mapper.ProductMapper;
import org.productservice.model.Product;
import org.productservice.repository.ProductRepository;
import org.productservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.map(productRequest);

        Product result = productRepository.save(product);

        log.info("Product created successfully");

        return productMapper.map(result);
    }

    @Override
    public List<ProductResponse> findAll() {
        return productMapper.map(productRepository.findAll());
    }


}
