package com.gorgona.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtility implements Serializable {

    private static final long serialVersionUID = 234234523523L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Este metodo ntiene como fin retornar el usuario al que esta asignado el token
     *
     * @param token
     * @return
     */
    public String getUsuarioFromToken ( String token) {
        return getConfiguracionToken (token, Claims::getSubject);
    }

    /**
     * Este metodo tiene como fin recuperar la fecha de vencimiento del token
     *
     * @param token
     * @return
     */
    public Date getFechaVencimientoToken ( String token) {
        return getConfiguracionToken (token, Claims::getExpiration);
    }


    /**
     * Este metodo tiene como fin retornar la configuracion del token
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T getConfiguracionToken ( String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getConfiguracionTokenKey (token);
        return claimsResolver.apply(claims);
    }

    /**
     * Metodo encargado de gestionar la clave secreta para obtener la configuracion del token
     * @param token
     * @return
     */
    private Claims getConfiguracionTokenKey ( String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }


    /**
     * Metodo encargado de verificar si el token esta vencido
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getFechaVencimientoToken (token);
        return expiration.before(new Date());
    }


    /**
     * Metodo encargado de generar un token a un usuario
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }


    /**
     * Mertodo encargado de:
     *  1. Definir configuracion del token
     *  2. Firmar el JWT usando clave secreya y algoritmo HS512
     * @param claims
     * @param subject
     * @return
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }


    /**
     * Metodo encargado de corroborar si un token es valido
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsuarioFromToken (token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
