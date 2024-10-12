package org.productservice.mapper.impl;

import org.productservice.dto.ProductRequest;
import org.productservice.dto.ProductResponse;
import org.productservice.mapper.ProductMapper;
import org.productservice.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product map(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.name())
                .skuCode(productRequest.skuCode())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
    }

    @Override
    public ProductResponse map(Product product) {
        return new ProductResponse(product.getId(), product.getSkuCode(),
                product.getName(), product.getDescription(), product.getPrice(), product.getThumbnailUrl());
    }

    @Override
    public List<ProductResponse> map(List<Product> productList) {
        return productList.stream()
                .map(p -> map(p))
                .toList();
    }
}
