package com.getir.readingisgood.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Pagination implements Serializable
{
    @NotNull
    @Min(1)
    private Integer size;

    @NotNull
    @Min(0)
    private Integer page;
}
