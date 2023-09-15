package com.example.service;

import com.example.entity.User;
import com.example.payload.request.LoginDTORequest;
import com.example.payload.request.UserDTOCreate;
import com.example.payload.response.BaseResponse;

public interface UserService {


    BaseResponse createAccount(UserDTOCreate accountDTOCreate);

    BaseResponse login(LoginDTORequest loginDTORequest);



}
