package cl.duoc.ms_usuario.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
 private static final String SECRET =
          "miClaveSuperSecretaYUltraSeguraDeMasDe32Caracteres";

    private static final Key KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes());

 public static String generarToken(String username){

    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(
                    new Date(System.currentTimeMillis() + 3600000)
            )
            .signWith(KEY)
            .compact();
}

    public static boolean validarToken(String token){

        try{

            Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);

            return true;

        }catch(Exception e){
            return false;
        }
    }
}
