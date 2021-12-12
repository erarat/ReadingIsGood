package com.getir.readingisgood.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class UpdateBookStockRequest implements Serializable
{
    @NotNull
    private Long bookId;

    @NotNull
    @Min(value = 0)
    private Integer quantity;
}
