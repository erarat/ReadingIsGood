package com.getir.readingisgood.service;

import com.getir.readingisgood.core.exception.BusinessException;
import com.getir.readingisgood.entity.BookEntity;
import com.getir.readingisgood.entity.StockEntity;
import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.request.PersistNewBookRequest;
import com.getir.readingisgood.model.request.UpdateBookStockRequest;
import com.getir.readingisgood.model.response.PersistNewBookResponse;
import com.getir.readingisgood.model.response.UpdateBookStockResponse;
import com.getir.readingisgood.reposityory.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookService
{
    private final BookRepository bookRepository;
    private final StockService stockService;

    public PersistNewBookResponse persistNewBook(PersistNewBookRequest request)
    {

        BookEntity bookServiceByIsbn = this.bookRepository.findByIsbn(request.getIsbn());
        if (null != bookServiceByIsbn)
        {
            throw new BusinessException("Book already registered! isbn: " + request.getIsbn());
        }

        var bookEntity = BookEntity.builder()
                .name(request.getName())
                .isbn(request.getIsbn())
                .author(request.getAuthor())
                .publishedYear(request.getPublishedYear())
                .price(request.getPrice())
                .build();

        var savedBook = bookRepository.save(bookEntity);;

        return PersistNewBookResponse.builder()
                .book(Book.builder()
                        .id(savedBook.getId())
                        .name(savedBook.getName())
                        .isbn(savedBook.getIsbn())
                        .author(savedBook.getAuthor())
                        .publishedYear(savedBook.getPublishedYear())
                        .price(savedBook.getPrice())
                        .build())
                .build();
    }

    public UpdateBookStockResponse updateBookStock(UpdateBookStockRequest request)
    {
        Optional<BookEntity> bookEntityOptional = this.bookRepository.findById(request.getBookId());
        if (bookEntityOptional.isEmpty())
        {
            throw new BusinessException("Book not found! BookId : " + request.getBookId());
        }

        var stockEntity = stockService.findByProductId(request.getBookId());
        if (null == stockEntity)
        {
            stockEntity = new StockEntity();
            stockEntity.setProduct(bookEntityOptional.get());
        }
        stockEntity.setQuantity(request.getQuantity());
        StockEntity updatedStock = stockService.save(stockEntity);

        return UpdateBookStockResponse.builder()
                .quantity(updatedStock.getQuantity())
                .bookId(updatedStock.getProduct().getId())
                .build();
    }

    public UpdateBookStockResponse trackBookStock(Long bookId)
    {
        Optional<BookEntity> bookEntityOptional = this.bookRepository.findById(bookId);
        if (bookEntityOptional.isEmpty())
        {
            throw new BusinessException("Book not found! BookId : " + bookId);
        }

        var stockEntity = stockService.findByProductId(bookId);
        if (null == stockEntity)
        {
            throw new BusinessException("No stock information! BookId : " + bookId);
        }

        return UpdateBookStockResponse.builder()
                .quantity(stockEntity.getQuantity())
                .bookId(stockEntity.getProduct().getId())
                .build();
    }

}
