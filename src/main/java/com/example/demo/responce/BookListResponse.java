package com.example.demo.responce;

import com.example.demo.entity.BookEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Data
public class BookListResponse extends BaseResponse{

    public BookListResponse(Iterable<BookEntity>data){
        super(true,"Книги");
        this.data=data;
    }
    private Iterable<BookEntity>data;

    @Override
    public  String toString() {

        return "Книги из библиотеки\n"+ data;

    }
}
