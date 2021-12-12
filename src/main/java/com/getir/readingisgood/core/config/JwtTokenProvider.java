package com.getir.readingisgood.core.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenProvider
{

    public static final String SECURE_KEY = "R34d1ng1SG00d";

    private JwtTokenProvider()
    {
    }

    public static String createToken(String email)
    {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + (15 * 60 * 60 * 1000));
        return Jwts.builder().setHeaderParam("email", email)
                .signWith(SignatureAlgorithm.HS256, SECURE_KEY)
                .setIssuedAt(now).setExpiration(expireTime).compact();
    }

    public static boolean isValidToken(String token)
    {
        try
        {
            Jwts.parser().setSigningKey(SECURE_KEY).parseClaimsJws(token);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static String findEmail(String token)
    {
        return (String) Jwts.parser().setSigningKey(SECURE_KEY).parseClaimsJws(token).getHeader()
                .get("email");
    }
}