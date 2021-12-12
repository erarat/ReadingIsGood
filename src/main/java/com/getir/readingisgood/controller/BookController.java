package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.PersistNewBookRequest;
import com.getir.readingisgood.model.request.UpdateBookStockRequest;
import com.getir.readingisgood.model.response.PersistNewBookResponse;
import com.getir.readingisgood.model.response.UpdateBookStockResponse;
import com.getir.readingisgood.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"Book Controller"}, description = "Create and update the books")
@RestController
@RequestMapping(value = "/api/v1",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class BookController
{

    private final BookService bookService;

    @PostMapping("books")
    @ApiOperation(value = "Create New Book")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "create new book"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<PersistNewBookResponse> persistNewBook(@RequestBody @Valid PersistNewBookRequest request)
    {
        return new ResponseEntity<>(bookService.persistNewBook(request), HttpStatus.CREATED);
    }

    @PutMapping("update-stock")
    @ApiOperation(value = "Update Book Stock")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "update book stock"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<UpdateBookStockResponse> updateBookStock(@RequestBody @Valid UpdateBookStockRequest request)
    {
        return new ResponseEntity<>(bookService.updateBookStock(request), HttpStatus.OK);
    }

    @GetMapping("/track-stock/{bookId}")
    @ApiOperation(value = "Track Stock")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "track book stock"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<UpdateBookStockResponse> updateBookStock(@PathVariable("bookId") Long bookId)
    {
        return new ResponseEntity<>(bookService.trackBookStock(bookId), HttpStatus.OK);
    }

}
