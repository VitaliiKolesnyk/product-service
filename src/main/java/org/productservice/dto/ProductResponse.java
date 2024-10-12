package org.productservice.dto;


public record ProductResponse(String id, String skuCode, String name,
                              String description, Double price, String thumbnailUrl){
        }
