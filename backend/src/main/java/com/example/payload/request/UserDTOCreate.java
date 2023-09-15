package com.example.payload.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTOCreate {
    private String fullName;
    private String username;
    private String password;
    private String phone;
    private String address;
    private List<String> roles;
}
