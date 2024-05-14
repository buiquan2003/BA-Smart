package Data;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class Token {
    public static String generateAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 1000)) 
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512)) 
                .compact();
    }

    public static String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000)) 
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512)) 
                .compact();
    }
}
