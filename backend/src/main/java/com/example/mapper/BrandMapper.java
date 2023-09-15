package com.example.mapper;

import com.example.entity.Brand;
import com.example.payload.request.BrandDTORequest;
import com.example.payload.response.BrandDTOResponse;

public class BrandMapper {

    public static Brand toBrand(BrandDTORequest brandDTORequest) {
        return Brand.builder()
                .name(brandDTORequest.getName())
                .image(brandDTORequest.getImage())
                .build();
    }
    public static BrandDTOResponse brandDTOResponse(Brand  brand) {
        return BrandDTOResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .image(brand.getImage())
                .build();
    }
}
