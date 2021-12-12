package com.getir.readingisgood.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class Order implements Serializable
{
    @NotEmpty
    private List<OrderLine> orderLines;

    @NotNull
    private Address address;
}
