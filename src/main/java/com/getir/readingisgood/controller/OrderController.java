package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.ListOrdersByDateIntervalRequest;
import com.getir.readingisgood.model.request.PersistNewOrderRequest;
import com.getir.readingisgood.model.request.QueryByIdOrderRequest;
import com.getir.readingisgood.model.response.ListOrdersByDateIntervalResponse;
import com.getir.readingisgood.model.response.PersistNewOrderResponse;
import com.getir.readingisgood.model.response.QueryByIdOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import com.getir.readingisgood.entity.OrderEntity;
import com.getir.readingisgood.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Order Controller"}, description = "Evertything about the orders")
@RestController
@RequestMapping(value = "/api/v1",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class OrderController
{
    private final OrderService orderService;

    @PostMapping("/orders")
    @ApiOperation(value = "Create New Order")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Create New Order"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<PersistNewOrderResponse> persistNewOrder(@RequestBody @Valid PersistNewOrderRequest request)
    {
        return new ResponseEntity<>(orderService.saveNewOrder(request), HttpStatus.CREATED);
    }


    @PostMapping("/get-order-by-id")
    @ApiOperation(value = "Get Order by Id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get Order by Id"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<QueryByIdOrderResponse> queryOrderById(@RequestBody @Valid QueryByIdOrderRequest request)
    {
        return new ResponseEntity<>(orderService.queryOrderById(request), HttpStatus.OK);
    }

    @PostMapping("/get-orders-by-date-interval")
    @ApiOperation(value = "Get Orders by Date Interval")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get Orders by Date Interval"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<ListOrdersByDateIntervalResponse> getOrdersByDateInterval(@RequestBody @Valid ListOrdersByDateIntervalRequest request)
    {
        return new ResponseEntity<>(orderService.listOrdersByDateInterval(request), HttpStatus.OK);
    }

}
