package com.getir.readingisgood.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class OrderLine implements Serializable
{
    private Long productId;

    private String productName;

    private Integer quantity;
}
