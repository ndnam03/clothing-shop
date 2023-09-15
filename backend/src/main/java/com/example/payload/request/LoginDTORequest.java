package com.example.payload.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTORequest {

    private String username;
    private String password;
}
