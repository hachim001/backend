package ma.emsi.myplatform.authentication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String generateToken(UserDetails userDetails, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return buildToken(claims, userDetails, jwtExpiration);
    }
    public String generateToken(Map<String,Object> claims,UserDetails userDetails){

        return buildToken(claims,userDetails,jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraclaims,
            UserDetails userDetails,
            long jwtExpiration)
    {

        var authorities=userDetails
                .getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                toList();
        return Jwts.builder().
                setClaims(extraclaims).
                setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .claim("authorities",authorities)
                .signWith(getSignInkey())
                .compact();
    }

    private Key getSignInkey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);

        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
