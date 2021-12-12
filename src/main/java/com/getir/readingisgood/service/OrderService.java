package com.getir.readingisgood.service;

import com.getir.readingisgood.core.config.SecurityFilter;
import com.getir.readingisgood.core.exception.BusinessException;
import com.getir.readingisgood.entity.*;
import com.getir.readingisgood.model.Address;
import com.getir.readingisgood.model.Order;
import com.getir.readingisgood.model.OrderLine;
import com.getir.readingisgood.model.request.ListOrdersByDateIntervalRequest;
import com.getir.readingisgood.model.request.PersistNewOrderRequest;
import com.getir.readingisgood.model.request.QueryByIdOrderRequest;
import com.getir.readingisgood.model.response.ListOrdersByDateIntervalResponse;
import com.getir.readingisgood.model.response.PersistNewOrderResponse;
import com.getir.readingisgood.model.response.QueryByIdOrderResponse;
import com.getir.readingisgood.reposityory.CustomerRepository;
import com.getir.readingisgood.reposityory.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import com.getir.readingisgood.reposityory.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService
{
    Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final CustomerRepository customerRepository;
    private final StockService stockService;
    private final AddressService addressService;
    private final ProductService productService;


    public PersistNewOrderResponse saveNewOrder(PersistNewOrderRequest request)
    {
        Lock lock = new ReentrantLock();
        lock.lock();

        var email = SecurityFilter.USER_EMAIL_TL.get();
        var customerEntity = customerRepository.findByEmail(email);

        var address = request.getOrder().getAddress();
        var orderLines = request.getOrder().getOrderLines();

        var orderEntity = new OrderEntity();
        orderEntity.setCustomer(customerEntity);

        var addressEntity = getAddressEntity(address.getAddressId());
        orderEntity.setAddress(addressEntity);

        orderRepository.save(orderEntity);

        for (var orderLine : orderLines)
        {
            persistNewOrderLineAndUpdateStock(orderEntity, orderLine);
        }

        lock.unlock();

        log.info("New order has been created");

        return PersistNewOrderResponse.builder()
                .order(Order.builder()
                        .orderLines(orderEntity.getOrderLines().stream()
                                .map(item -> OrderLine.builder()
                                        .productId(item.getProduct().getId())
                                        .productName(item.getProduct().getName())
                                        .quantity(item.getAmount())
                                        .build())
                                .collect(Collectors.toList()))
                        .address(Address.builder()
                                .addressId(addressEntity.getId())
                                .country(addressEntity.getCountry())
                                .city(addressEntity.getCity())
                                .description(addressEntity.getDescription())
                                .build())
                        .build())
                .build();
    }

    private void persistNewOrderLineAndUpdateStock(OrderEntity orderEntity, OrderLine orderLine)
    {
        var stockEntity = getStockEntity(orderLine.getProductId());
        if (stockEntity.getQuantity() < orderLine.getQuantity())
        {
            log.info("There is no stock! ProductId : " + orderLine.getProductId());
            throw new BusinessException("There is no stock! ProductId : " + orderLine.getProductId());
        }
        var orderLineEntity = new OrderLineEntity();
        orderLineEntity.setAmount(orderLine.getQuantity());

        var productEntity = getProductEntity(orderLine.getProductId());
        orderLineEntity.setProduct(productEntity);
        orderLineEntity.setOrder(orderEntity);
        orderEntity.getOrderLines().add(orderLineEntity);
        var savedOrderLine = orderLineRepository.save(orderLineEntity);
        stockEntity.updateStock(savedOrderLine.getAmount());
        stockService.save(stockEntity);
        log.info("{}'s stock has been updated : " + orderLine.getProductId());

    }


    private StockEntity getStockEntity(Long id)
    {
        var stockEntity = stockService.findByProductId(id);
        if (null == stockEntity)
        {
            log.info("No products in stock! ID : {}", id);
            throw new BusinessException("No products in stock! ID : " + id);
        }
        return stockEntity;
    }

    private ProductEntity getProductEntity(Long id)
    {
        var productEntityOptional = productService.findById(id);
        if (productEntityOptional.isEmpty())
        {
            log.info("Product not found! ID : {}", id);
            throw new BusinessException("Product not found! ID : " + id);
        }
        return productEntityOptional.get();
    }

    private AddressEntity getAddressEntity(Long id)
    {
        var addressEntityOptional = addressService.findById(id);
        if (addressEntityOptional.isEmpty())
        {
            log.info("Address not found! ID : {}", id);
            throw new BusinessException("Address not found! ID : " + id);
        }
        return addressEntityOptional.get();
    }

    @Transactional
    public QueryByIdOrderResponse queryOrderById(QueryByIdOrderRequest request)
    {
        var orderId = request.getOrderId();

        var orderEntityOptional = orderRepository.findById(orderId);
        if (orderEntityOptional.isEmpty())
        {
            throw new BusinessException("Order not found! ID : " + orderId);
        }
        OrderEntity orderEntity = orderEntityOptional.get();
        return QueryByIdOrderResponse.builder()
                .order(convertOrderEntityToOrderDto(orderEntity))
                .build();
    }

    @Transactional
    public ListOrdersByDateIntervalResponse listOrdersByDateInterval(ListOrdersByDateIntervalRequest request)
    {
        var startDate = request.getStartDate();
        var endDate = request.getEndDate();

        var orders = orderRepository.listOrdersByDateInterval(startDate, endDate);
        return ListOrdersByDateIntervalResponse.builder()
                .order(convertOrderEntityToOrderDto(orders))
                .build();
    }

    private List<Order> convertOrderEntityToOrderDto(List<OrderEntity> orders)
    {
        var result = new ArrayList<Order>();
        for (OrderEntity orderEntity : orders)
        {
            var order = Order.builder()
                    .orderLines(convertOrderLineEntityToOrderLineDto(orderEntity.getOrderLines()))
                    .address(Address.builder()
                            .country(orderEntity.getAddress().getCountry())
                            .city(orderEntity.getAddress().getCity())
                            .description(orderEntity.getAddress().getDescription())
                            .build())
                    .build();
            result.add(order);

        }
        return result;
    }

    private Order convertOrderEntityToOrderDto(OrderEntity orderEntity)
    {
        return Order.builder()
                .orderLines(convertOrderLineEntityToOrderLineDto(orderEntity.getOrderLines()))
                .address(Address.builder()
                        .addressId(orderEntity.getAddress().getId())
                        .country(orderEntity.getAddress().getCountry())
                        .city(orderEntity.getAddress().getCity())
                        .description(orderEntity.getAddress().getDescription())
                        .build())
                .build();
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

    public List<OrderEntity> queryAllOrdersOCustomerId(Long customerId, Integer page, Integer size)
    {
        Pageable paging = PageRequest.of(page, size);
        return orderRepository.queryAllOrdersOCustomerId(customerId, paging);
    }

}
