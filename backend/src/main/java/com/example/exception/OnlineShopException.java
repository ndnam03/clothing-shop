package com.example.exception;

import com.example.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Data
@Builder
@ResponseStatus(HttpStatus.NOT_FOUND)
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OnlineShopException extends RuntimeException{
    String error;

    public OnlineShopException(String error) {
        super(error); // Gọi constructor của lớp cha với thông điệp lỗi
        this.error = error;
    }
}
