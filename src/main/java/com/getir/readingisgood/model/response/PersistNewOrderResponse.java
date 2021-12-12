package com.getir.readingisgood.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.getir.readingisgood.model.Order;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class PersistNewOrderResponse implements Serializable
{
    private Order order;
}
