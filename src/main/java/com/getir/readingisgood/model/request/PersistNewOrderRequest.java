package com.getir.readingisgood.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.getir.readingisgood.model.Order;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class PersistNewOrderRequest implements Serializable
{
    @NotNull
    private Order order;
}
