package com.getir.readingisgood.service;

import com.getir.readingisgood.core.config.JwtTokenProvider;
import com.getir.readingisgood.core.config.SecurityFilter;
import com.getir.readingisgood.core.exception.BusinessException;
import com.getir.readingisgood.entity.AddressEntity;
import com.getir.readingisgood.entity.CustomerEntity;
import com.getir.readingisgood.entity.OrderEntity;
import com.getir.readingisgood.entity.OrderLineEntity;
import com.getir.readingisgood.model.Address;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.Order;
import com.getir.readingisgood.model.OrderLine;
import com.getir.readingisgood.model.request.LoginRequest;
import com.getir.readingisgood.model.request.PersistNewCustomerRequest;
import com.getir.readingisgood.model.request.QueryAllOrdersOfCustomerRequest;
import com.getir.readingisgood.model.response.PersistNewCustomerResponse;
import com.getir.readingisgood.model.response.QueryAllOrdersOfCustomerResponse;
import com.getir.readingisgood.reposityory.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService
{
    Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final OrderService orderService;

    public PersistNewCustomerResponse saveNewCustomer(PersistNewCustomerRequest request)
    {
        CustomerEntity customerEntity = customerRepository.findByEmail(request.getEmail());
        if (null != customerEntity)
        {
            throw new BusinessException("Customer already saved! + Email : " + request.getEmail());
        }

        var addressEntity = AddressEntity.builder()
                .city(request.getAddress().getCity())
                .country(request.getAddress().getCountry())
                .description(request.getAddress().getDescription())
                .build();
        addressService.save(addressEntity);

        customerEntity = CustomerEntity.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .address(addressEntity)
                .build();

        var savedCustomerEntity = customerRepository.save(customerEntity);

        log.info("New customer has been created {}", savedCustomerEntity);

        return PersistNewCustomerResponse.builder()
                .customer(Customer.builder()
                        .customerId(savedCustomerEntity.getId())
                        .name(savedCustomerEntity.getName())
                        .email(savedCustomerEntity.getEmail())
                        .build())
                .build();

    }

    public QueryAllOrdersOfCustomerResponse queryAllOrdersOfTheCustomer(QueryAllOrdersOfCustomerRequest request)
    {
        String email = SecurityFilter.USER_EMAIL_TL.get();

        CustomerEntity customerEntity = customerRepository.findByEmail(email);
        var orders = orderService.queryAllOrdersOCustomerId(customerEntity.getId(), request.getPagination().getPage(), request.getPagination().getSize());
        return QueryAllOrdersOfCustomerResponse.builder().orders(convertOrderEntityToOrderDto(orders)).build();
    }

    public String login(LoginRequest request)
    {
        CustomerEntity customer = customerRepository.findByEmail(request.getEmail());
        if (customer == null || !customer.getPassword().equals(request.getPassword()))
        {
            log.error("Login attempt with invalid user");
            throw new BusinessException("Invalid User!");
        }
        return JwtTokenProvider.createToken(customer.getEmail());
    }

    private List<Order> convertOrderEntityToOrderDto(List<OrderEntity> orders)
    {
        var result = new ArrayList<Order>();
        for (OrderEntity orderEntity : orders)
        {
            var order = Order.builder()
                    .orderLines(convertOrderLineEntityToOrderLineDto(orderEntity.getOrderLines()))
                    .address(Address.builder()
                            .addressId(orderEntity.getAddress().getId())
                            .country(orderEntity.getAddress().getCountry())
                            .city(orderEntity.getAddress().getCity())
                            .description(orderEntity.getAddress().getDescription())
                            .build())
                    .build();
            result.add(order);
        }
        return result;
    }

    private List<OrderLine> convertOrderLineEntityToOrderLineDto(List<OrderLineEntity> orderLines)
    {
        var result = new ArrayList<OrderLine>();
        for (OrderLineEntity orderLineEntity : orderLines)
        {
            var orderLine = OrderLine.builder()
                    .productId(orderLineEntity.getProduct().getId())
                    .quantity(orderLineEntity.getAmount())
                    .productName(orderLineEntity.getProduct().getName())
                    .build();
            result.add(orderLine);
        }
        return result;
    }


}