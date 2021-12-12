package com.getir.readingisgood.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class QueryByIdOrderRequest implements Serializable
{
    @NotNull
    private Long orderId;
}
