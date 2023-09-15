package com.example.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDTOResponse {

    private Long id;
    private String name;
    private String image;
}
