package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;


@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class BookEntity {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private String kind;

    @Override
    public String toString() {
        return

                "НАзвание: " + title + '\'' +
                "автор: " + author + '\'' +
                "Издательство: " + publisher + '\'' +
                "ГОд издания: " + year +
                "Жанр: " + kind + '\'';
    }
}
