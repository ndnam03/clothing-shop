package com.example.payload.response;

import com.example.entity.ERole;
import com.example.entity.Role;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTOResponse {
    private Long id;
    private String fullName;
    private String username;
    private String phone;
    private String address;
    private String listRoles;
}
