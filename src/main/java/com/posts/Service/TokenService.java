package com.posts.Service;

import com.posts.Security.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mumarm45 on 05/08/2017.
 */
@Service
public class TokenService {

    final static String CLAIM_SUBJECT = "sub";


    @Autowired
    private TimeService timeService;

    @Value("${auth.secret}")
    private String secret;

    public String createToken(UserDetails userDetails) {
        Map<String, Object> claim = new HashMap<>();
        final Date createDate = timeService.now();
        final Date expirationDate = new Date(createDate.getTime() * 604800);
        claim.put(CLAIM_SUBJECT, userDetails.getUsername());
        claim.put("date", createDate);

        return Jwts.builder()
                .setClaims(claim)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public Claims getClaimFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimFromToken(token);
            username = claims.getSubject();
        } catch (Exception ex) {
            username = null;
        }
        return username;
    }

    public boolean isTokenExpire(String token) {
        boolean isExpired;
        try {
            Date expireDate = getExpireDateFromToken(token);

            isExpired = expireDate.before(timeService.now());
        } catch (Exception ex) {
            isExpired = false;
        }

        return isExpired;

    }

    public Date getExpireDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimFromToken(token);
            created = claims.getExpiration();
        } catch (Exception ex) {
            created = null;
        }

        return created;
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        String username = getUsernameFromToken(token);
        Date expireDate = getExpireDateFromToken(token);
        return (user.getUsername().equals(username) && !isTokenExpire(token));
    }


    public Date getCreateDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimFromToken(token);
            created = new Date((Long)claims.get("date"));
        } catch (Exception ex) {
            created = null;
        }

        return created;
    }

    public String refreshToken(String token,User user) {
      String refreshtoken;
      try{
          if(validateToken(token,user)){
               refreshtoken = createToken(user);
          }
          else{
              refreshtoken = null;
          }
      }catch (Exception ex){
         refreshtoken = null;
      }

      return refreshtoken;
    }
}
