package com.getir.readingisgood.core.config;

import io.jsonwebtoken.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@NoArgsConstructor
public class JwtTokenProvider
{
    public static final String SECURE_KEY = "R34d1ng1SG00d";

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
        try {
            Jwts.parser().setSigningKey(SECURE_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    public static String findEmail(String token)
    {
        return (String) Jwts.parser().setSigningKey(SECURE_KEY).parseClaimsJws(token).getHeader()
                .get("email");
    }
}