package ru.shuman.Project_Aibolit_Server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    private final String SUBJECT = "User details";
    private final String ISSUER = "Aibolit software";
    private final int EXPIRATION_DATE_IN_MINUTES = 60 * 24 * 365;

    @Value("${jwt_secret}")
    private String secretKey;

    public String generateToken(String username) {
        Date exparationDate = Date.from(ZonedDateTime.now().plusMinutes(EXPIRATION_DATE_IN_MINUTES).toInstant());

        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(exparationDate)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = jwtVerifier.verify(token);

        return jwt.getClaim("username").asString();
    }

}
