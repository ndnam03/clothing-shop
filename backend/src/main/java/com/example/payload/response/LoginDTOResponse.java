package com.example.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTOResponse {
    private String type ;
    private String token;

    UserDTOResponse userDTOResponse;
}
