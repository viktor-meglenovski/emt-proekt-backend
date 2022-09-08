package threed.manager.backend.sharedkernel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import threed.manager.backend.sharedkernel.security.exceptions.NotAuthorisedAccessException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtValidator {
    public static Jws<Claims> validateToken(String token){
        Key hmacKey = new SecretKeySpec("mySecret".getBytes(), SignatureAlgorithm.HS512.getJcaName());

        Jws<Claims> jwt = Jwts.parser()
                .setSigningKey(hmacKey)
                .parseClaimsJws(token.replace("Bearer ",""));
        Date now=new Date();
        if(jwt.getBody().getExpiration().getTime()<=now.getTime()){
            throw new NotAuthorisedAccessException();
        }
        return jwt;
    }
}
