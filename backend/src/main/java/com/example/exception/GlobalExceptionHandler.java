package com.example.exception;


import com.example.entity.User;
import com.example.payload.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OnlineShopException.class)
    public ResponseEntity<BaseResponse> handleOnlineShopException(OnlineShopException ex){
            BaseResponse baseResponse = BaseResponse.builder()
                    .code(String.valueOf(HttpStatus.NOT_FOUND.toString()))
                    .data(ex.getLocalizedMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);
    }
}
