package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

public class JwtUtils {
    //生成token
    public static String creatJWT(String secretKey, long ttl, Map<String, Object> claims){
        //设置头部签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //jwt截止时间
        long expMillis = ttl + System.currentTimeMillis();
        Date exp = new Date(expMillis);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                .setExpiration(exp);

        return jwtBuilder.compact();
    }

    //解密token
    public static Claims parseJW(String secretKey,String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();
        return claims;

    }
}
