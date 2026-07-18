package com.group.purchase.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final long accessTokenValidityTime = 1000L * 60 * 60;
    //토큰의 유효 기간을 설정

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        //application.yml에 작성해둔 jwt.secret 값을 읽어와서 secretKey 매개변수에 넣음
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        //문자열로 된 secretKey가 Base64로 인코딩되어 있다고 가정하고, 이를 해독하여 바이트 배열(byte[])로 변환
        this.key = Keys.hmacShaKeyFor(keyBytes);
        //HMAC-SHA 알고리즘에 적합한 강력한 Key 객체를 생성하고, 앞서 선언한 멤버 변수 key에 할당
    }

    public String createToken(String email) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenValidityTime);
        //현재 시간(now.getTime())에 아까 설정한 유효 기간(1시간)을 더하여 토큰의 만료 시간을 계산

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
