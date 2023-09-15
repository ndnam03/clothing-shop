package com.example.payload.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTORequest {

    private String name;

    private String image;


}
