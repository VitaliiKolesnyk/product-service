package org.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.productservice.dto.ProductRequest;
import org.productservice.dto.ProductResponse;
import org.productservice.event.ProductEvent;
import org.productservice.mapper.ProductMapper;
import org.productservice.model.Product;
import org.productservice.repository.ProductRepository;
import org.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final S3Service s3Service;

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.map(productRequest);

        Product result = productRepository.save(product);

        log.info("Product created successfully");

        sendEventToKafka("CREATE", result);

        return productMapper.map(result);
    }

    @Override
    public Page<ProductResponse> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        Page<ProductResponse> productResponsePage = products.map(product -> new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSkuCode(),
                product.getDescription(),
                product.getPrice(),
                product.getThumbnailUrl()
        ));

        return productResponsePage;
    }

    @Override
    public void delete(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);

        log.info("Product deleted successfully");

        sendEventToKafka("DELETE", product);
    }

    public String uploadThumbnail(MultipartFile file, String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        String thumbnailUrl = s3Service.uploadFile(file);

        product.setThumbnailUrl(thumbnailUrl);

        productRepository.save(product);

        return thumbnailUrl;
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private void sendEventToKafka(String action, Product product) {
        ProductEvent productEvent = new ProductEvent(action, product.getName(), product.getSkuCode(), product.getDescription(),
                product.getPrice(), product.getThumbnailUrl());

        log.info("Start - Sending productEvent {} to Kafka topic product-events", productEvent);

        kafkaTemplate.send("product-events", productEvent);

        log.info("End - Sending productEvent to Kafka topic product-events");
    }
}
