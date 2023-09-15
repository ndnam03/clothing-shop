package com.example.controller;

import com.example.payload.response.BaseResponse;
import com.example.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.utils.Constant.API_VERSION;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(API_VERSION + "/brand")
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/add-brand")
    public ResponseEntity<BaseResponse> createBrand(@RequestParam("name") String name,
                                                    @RequestParam("file") MultipartFile image) {
        try {
            BaseResponse response = brandService.createBrand(name, image);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setCode("error");
            errorResponse.setSuccess(false);
            errorResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse> getAll() {
        return ResponseEntity.ok(brandService.getAll());
    }
}
