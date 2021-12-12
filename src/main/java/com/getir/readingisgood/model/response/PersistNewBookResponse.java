package com.getir.readingisgood.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.getir.readingisgood.model.Book;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder
public class PersistNewBookResponse implements Serializable
{
    private Book book;
}
