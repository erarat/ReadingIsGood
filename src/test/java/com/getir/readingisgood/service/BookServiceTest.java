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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private StockService stockService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPersistNewBook(){
        BookEntity bookEntity = BookEntity.builder().isbn("1234567").author("Martin Eden").build();
        BookEntity bookEntityResult = BookEntity.builder().id(1l).isbn("1234567").author("Martin Eden").build();

        PersistNewBookRequest persistNewBookRequest = PersistNewBookRequest.builder().isbn("1234567").author("Martin Eden").build();

        when(bookRepository.save(bookEntity)).thenReturn(bookEntityResult);
        assertEquals("1234567",bookService.persistNewBook(persistNewBookRequest).getBook().getIsbn());
    }


    @Test(expected = BusinessException.class)
    public void testPersistNewBookBusinessException(){

        BookEntity bookEntity = BookEntity.builder().isbn("1234567").author("Martin Eden").build();
        BookEntity bookEntityResult = BookEntity.builder().id(1l).isbn("1234567").author("Martin Eden").build();

        PersistNewBookRequest persistNewBookRequest = PersistNewBookRequest.builder().isbn("1234567").author("Martin Eden").build();

        when(bookRepository.findByIsbn("1234567")).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntityResult);
        bookService.persistNewBook(persistNewBookRequest);
    }
}