package com.example.doantotnghiep.Services.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtil {
    private static final String
            SECRET_KEY = "TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI=";
    public static String getUsernameFromJwt(String jwt) {
        try {
            final String token = jwt.substring(7);
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String username = claims.get("sub", String.class);
            return username;
        } catch (Exception e) {
            return null;
        }
    }
}
