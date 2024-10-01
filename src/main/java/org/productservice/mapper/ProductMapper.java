package org.productservice.mapper;



import org.productservice.dto.ProductRequest;
import org.productservice.dto.ProductResponse;
import org.productservice.model.Product;

import java.util.List;

public interface ProductMapper {

    Product map(ProductRequest productRequest);

    ProductResponse map(Product product);

    List<ProductResponse> map(List<Product> productList);
}
