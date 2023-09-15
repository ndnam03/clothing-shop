package com.example.jwt;

import com.example.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//4
@Slf4j

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private String getJwtFromRequest(HttpServletRequest request) {
        // lấy chuỗi token từ Authorization header
        String bearerToken  = request.getHeader("Authorization");
        // kiểm tra header Authorization có chứa thông tin jwt không
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // lấy jwt từ request
            String jwt = getJwtFromRequest(request);
            if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                // lấy username từ chuỗi jwt
                String username = jwtTokenProvider.getUsernameFromJwt(jwt);
                // lấy thông tin người dùng từ userid
                //Security  làm việc với UserDetails not UserCustom
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if(userDetails != null) {
                    // Nếu người dùng hợp lệ set thông tin cho security context
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,userDetails.getAuthorities()); // cung cấp các quyền của user
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                   SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }catch (Exception e) {
            log.error("Fail on set user authentication", e);
        }
        filterChain.doFilter(request,response);
    }
}
