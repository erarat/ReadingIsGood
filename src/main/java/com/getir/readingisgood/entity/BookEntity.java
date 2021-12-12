package com.getir.readingisgood.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "BOOK")
@EqualsAndHashCode(callSuper = true)
public class BookEntity extends ProductEntity
{

    @Column(name = "ISBN",unique = true)
    private String isbn;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PUBLISHED_YEAR")
    private LocalDate publishedYear;
}
