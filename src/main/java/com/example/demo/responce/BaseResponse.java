package com.example.demo.responce;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseResponse {//класс позволяющий понять прошкл запрос или нет
protected boolean success;
protected String message;
}
