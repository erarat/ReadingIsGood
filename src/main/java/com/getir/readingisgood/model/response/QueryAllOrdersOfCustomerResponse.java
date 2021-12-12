package com.getir.readingisgood.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.getir.readingisgood.model.Order;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class QueryAllOrdersOfCustomerResponse implements Serializable
{
    private List<Order> orders;
}

