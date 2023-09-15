package com.example.jwt;

import com.example.security.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.tree.ExpandVetoException;
import java.util.Date;

//3 trả về chuỗi token
@Component
@Slf4j //ghi log
public class JwtTokenProvider {
    // key chữ ký
    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;
    // thời gian hết hạn của JWT
    @Value("${ra.jwt.expiration}")
    private int JWT_EXPIRATION;

    // Tạo ra jwt từ thông tin của user
    public String generateToken(CustomUserDetails customUserDetails) {
        Date now = new Date();
        // ngày hết hiệu lực
        Date dateExpired = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi JSON TOKEN từ username của user
        // mã hóa rồi trả lại
        return Jwts.builder().setSubject(customUserDetails.getUsername())
                .setIssuedAt(now) // ngày hiệu lực
                .setExpiration(dateExpired) // ngày hết hạn
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    // Mã hóa ngược lại để lấy thông tin từ jwt
    public String getUsernameFromJwt(String token) {
        // lấy ra tất cả các Claims của json token
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        // trả lại username
        return claims.getSubject();
    }

    // Validate thông tin của chuỗi jwt
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token");
        }catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException JWT Token");
        }catch (UnsupportedJwtException e) {
            log.error("UnsupportedJwtException JWT Token");
        }catch (IllegalArgumentException e) {
            log.error("JWT claims String is empty");
        }
        return false;
    }

}
