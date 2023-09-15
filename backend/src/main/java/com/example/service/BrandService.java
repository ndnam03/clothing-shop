package com.example.service;

import com.example.payload.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BrandService {

    BaseResponse createBrand(String name, MultipartFile imageBrand) throws IOException;
    BaseResponse getAll();
}
