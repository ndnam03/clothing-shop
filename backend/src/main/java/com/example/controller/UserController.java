package com.example.controller;

import com.example.payload.request.LoginDTORequest;
import com.example.payload.request.UserDTOCreate;
import com.example.payload.response.BaseResponse;
import com.example.payload.response.UserDTOResponse;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static com.example.utils.Constant.API_VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/auth")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> createUser(@RequestBody UserDTOCreate userDTOCreate) {

        return  ResponseEntity.ok(userService.createAccount(userDTOCreate));
    }


    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginDTORequest loginDTORequest) {

        return  ResponseEntity.ok(userService.login(loginDTORequest));
    }
}
