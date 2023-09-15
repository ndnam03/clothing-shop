package com.example.mapper;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.payload.request.UserDTOCreate;
import com.example.payload.response.LoginDTOResponse;
import com.example.payload.response.UserDTOResponse;

public class UserMapper {



    public static User toUser(UserDTOCreate userDTOCreate) {

        return User.builder()
                .fullName(userDTOCreate.getFullName())
                .username(userDTOCreate.getUsername())
                .password(userDTOCreate.getPassword())
                .phone(userDTOCreate.getPhone())
                .address(userDTOCreate.getAddress())
                .build();
    }

    public static UserDTOResponse userDTOResponse(User user) {
        String roleName = user.getRoles().stream()
                .map(Role::getRoleName)
                .findFirst()
                .orElse("");
        return UserDTOResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .phone(user.getPhone())
                .address(user.getAddress())
                .listRoles(roleName )
                .build();
    }

    public static LoginDTOResponse tokenPayLoad(User user) {
        return LoginDTOResponse.builder()
                .userDTOResponse(UserMapper.userDTOResponse(user))
                .type("Bearer")
                .build();
    }
}
