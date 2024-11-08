package com.example.REST_Server_With_JWT.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

// Класс, который будет работать с jwt токенами
// в этом классе будем генерировать jwt токен для передачи его клиенту, и будем его валидировать, когда получим его от клиента
@Component
public class JWTUtil {

    private final String ISSUER = "ShumanAV_App";
    private final String SUBJECT = "User details";

    // секретное выражение хранится во внешнем файле application.properties
    @Value("${jwt_secret}")
    private String secret;

    // в этом методе будем генерировать токен на основании имени пользователя, т.е. будем в токене хранить имя пользователя
    public String generateToken(String username) {
        //укажем срок годности токена от текущего времени в текущей временной зоне, плюс 60 минут от текущего времени, переводим в timestamp
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        // теперь сгенерируем jwt токен, автоматически генерируется header, мы формируем тело токена (тема, клаймы, дата создания, кто выдал,
        // дата окончания дейcтвия токена), а также подпись - SIGNATURE при помощи секрета
        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim("name", username)
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    // метод, который будем вызывать когда клиент будет присылать запрос, и мы здесь будем валидировать токен
    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        // сначала построим верификатор для верификации токена
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))    // у токена должен быть такой же секрет
                .withSubject(SUBJECT)                                       // у токена должен быть такой же сабджект (тема)
                .withIssuer(ISSUER)                                         // токен должен быть выдан тем же
                .build();

        // провалидируем токен с помощью секрета, поймем выдан ли он нами
        // если на этапе верификации токен не прошел ее, то будет выброшено исключение JWTVerificationException
        DecodedJWT jwt = jwtVerifier.verify(token);

        // возвращаем извлеченный username из токена
        return jwt.getClaim("name").asString();
    }



}
