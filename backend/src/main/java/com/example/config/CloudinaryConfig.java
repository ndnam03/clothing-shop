package com.example.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dksz4so8q",
                "api_key", "514347244769618",
                "api_secret", "oFCGol9QW8sA0bk_qr816NNOb3I",
                "secure", true));
        return  cloudinary;
    }
}
