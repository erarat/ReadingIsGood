package com.getir.readingisgood.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder
public class Book implements Serializable
{
    private Long id;

    private String name;

    private String isbn;

    private String author;

    private LocalDate publishedYear;

    private BigDecimal price;

}
