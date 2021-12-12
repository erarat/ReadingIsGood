package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.ProductEntity;
import com.getir.readingisgood.reposityory.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;

    public Optional<ProductEntity> findById(Long id)
    {
        return productRepository.findById(id);
    }
}
