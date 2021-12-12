package com.getir.readingisgood.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.getir.readingisgood.core.exception.BusinessException;
import com.getir.readingisgood.entity.AddressEntity;
import com.getir.readingisgood.entity.BookEntity;
import com.getir.readingisgood.entity.StockEntity;
import com.getir.readingisgood.model.request.PersistNewBookRequest;
import com.getir.readingisgood.reposityory.AddressRepository;
import com.getir.readingisgood.reposityory.BookRepository;
import com.getir.readingisgood.reposityory.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

    @MockBean
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByProductId(){
        StockEntity stockEntity = StockEntity.builder().id(1l).quantity(100).build();

        when(stockRepository.findByProductId(1l)).thenReturn(stockEntity);
        assertEquals(100,stockService.findByProductId(1l).getQuantity().intValue());
    }


    public void testSave(){
        StockEntity stockEntity = StockEntity.builder().id(1l).quantity(100).build();

        when(stockRepository.save(stockEntity)).thenReturn(stockEntity);
        assertEquals(100,stockService.save(stockEntity).getQuantity().intValue());
    }
}