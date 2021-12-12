package com.getir.readingisgood.controller;


import com.getir.readingisgood.model.request.LoginRequest;
import com.getir.readingisgood.model.request.PersistNewCustomerRequest;
import com.getir.readingisgood.model.request.QueryAllOrdersOfCustomerRequest;
import com.getir.readingisgood.model.response.PersistNewCustomerResponse;
import com.getir.readingisgood.model.response.QueryAllOrdersOfCustomerResponse;
import com.getir.readingisgood.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Api(tags = {"Customer Controller"}, description = "Evertything about the customers")
@RestController
@RequestMapping(value = "/api/v1",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@SwaggerDefinition(securityDefinition = @SecurityDefinition(apiKeyAuthDefinitions = {@ApiKeyAuthDefinition(name = "user", key = "Authorization", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER)}))
public class CustomerController
{
    @Autowired
    CustomerService customerService;

    @PostMapping("/customers")
    @ApiOperation(value = "Create New Customer")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "create new customer"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<PersistNewCustomerResponse> persistNewCustomer(@RequestBody @Valid PersistNewCustomerRequest request)
    {
        return new ResponseEntity<>(customerService.saveNewCustomer(request), HttpStatus.CREATED);
    }

    @PostMapping("/get-orders-by-customer")
    @ApiOperation(value = "Query All Orders of The Customer")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "query all orders of the customer"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<QueryAllOrdersOfCustomerResponse> queryAllOrdersOfTheCustomer(@RequestBody @Valid QueryAllOrdersOfCustomerRequest request)
    {
        return new ResponseEntity<>(customerService.queryAllOrdersOfTheCustomer(request), HttpStatus.OK);
    }

    @PostMapping("login")
    @ApiOperation(value = "Login Customer")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Login Customer"), @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request)
    {
        return new ResponseEntity<>(customerService.login(request), HttpStatus.OK);
    }

}
