package com.example.service.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.entity.Brand;
import com.example.mapper.BrandMapper;
import com.example.payload.request.BrandDTORequest;
import com.example.payload.response.BaseResponse;

import com.example.payload.response.BrandDTOResponse;
import com.example.repository.BrandRepository;
import com.example.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final Cloudinary cloudinary;

    @Override
    public BaseResponse createBrand(String name, MultipartFile imageBrand) throws IOException {
        BaseResponse baseResponse = new BaseResponse();

        Map r = cloudinary.uploader().upload(imageBrand.getBytes(),
                ObjectUtils.asMap("resource_type", "auto",
                        "folder", "vue-spring"));
        String image = (String) r.get("secure_url");


        BaseResponse validateResult = validateBrand(name);
        if (validateResult.isSuccess()) {
            BrandDTORequest brandDTORequest = BrandDTORequest.builder()
                    .name(name)
                    .image(image)
                    .build();

            Brand brand = brandRepository.save(BrandMapper.toBrand(brandDTORequest));
            baseResponse.setSuccess(true);
            baseResponse.setCode(String.valueOf(HttpStatus.OK));
            baseResponse.setData(BrandMapper.brandDTOResponse(brand));
        } else {
            baseResponse.setCode(validateResult.getCode());
            baseResponse.setSuccess(validateResult.isSuccess());
            baseResponse.setData(validateResult.getData());
        }


        return baseResponse;
    }

    @Override
    public BaseResponse getAll() {
        BaseResponse baseResponse = new BaseResponse();
        List<Brand> brands = brandRepository.findAll();
        List<BrandDTOResponse> brandDTOResponses =
                brands.stream().map(brand -> BrandMapper.brandDTOResponse(brand))
                        .collect(Collectors.toList());
        baseResponse.setSuccess(true);
        baseResponse.setData(brandDTOResponses);
        baseResponse.setCode(String.valueOf(HttpStatus.OK));
        return baseResponse;
    }

    private BaseResponse validateBrand(String name) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        Brand brand = brandRepository.findByName(name);

        if (!org.springframework.util.ObjectUtils.isEmpty(brand)) {
            baseResponse.setSuccess(false);
            baseResponse.setData("Brand name is not exits");
            baseResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
            return baseResponse;
        }
        return baseResponse;
    }
}
