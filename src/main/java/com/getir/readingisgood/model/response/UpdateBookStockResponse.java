package com.getir.readingisgood.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class UpdateBookStockResponse implements Serializable
{
    private Long bookId;

    private Integer quantity;
}
