package com.example.config;

import com.example.jwt.JwtAuthenticationFilter;
import com.example.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//5
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)


public class AppConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get authenticationManager bean
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //Password encoder, để Spring Security sử dựng mã hóa password
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // cung cấp customUserDetailService cho spring security
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());// cung cấp password encoder
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Vô hiệu hóa CSRF
                .authorizeRequests() // Phân quyền cho các yêu cầu
                .antMatchers("/api/v1/auth/**").permitAll() // Cho phép tất cả mọi người truy cập vào các đường dẫn /api/v1/auth/**
                .antMatchers("/api/v1/**").permitAll() // Cho phép tất cả mọi người truy cập vào các đường dẫn /api/v1/test/**
                .anyRequest().authenticated(); // Các yêu cầu khác yêu cầu xác thực

        // Thêm một bộ lọc kiểm tra JWT trước bộ lọc xác thực UsernamePassword
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
