package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.StockEntity;
import lombok.RequiredArgsConstructor;
import com.getir.readingisgood.reposityory.StockRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService
{
    private final StockRepository stockRepository;

    public StockEntity findByProductId(Long productId)
    {
        return stockRepository.findByProductId(productId);
    }

    public StockEntity save(StockEntity stockEntity)
    {
        return stockRepository.save(stockEntity);
    }
}
