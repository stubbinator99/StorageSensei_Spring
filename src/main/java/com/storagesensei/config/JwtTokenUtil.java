package com.storagesensei.config;

import com.storagesensei.db.CustomUserDetails;
import com.storagesensei.models.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static java.lang.String.format;

@Component
public class JwtTokenUtil {
  private final String jwtSecret = "wGrm67RDAeFGLnIjFU28dSpg7";
  private final String jwtIssuer = "example.io";

  /*private final Logger logger;

  public JwtTokenUtil(Logger logger) {
    this.logger = logger;
  }*/

  public String generateAccessToken(/*User user*/CustomUserDetails user) {
    return Jwts.builder()
        //.setSubject(format("%s,%s", user.getId(), user.getUsername()))
        .setSubject(user.getUsername())
        .setIssuer(jwtIssuer)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  /*public String getUserId(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject().split(",")[0];
  }*/

  public String getUsername(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();

    //return claims.getSubject().split(",")[1];
    return claims.getSubject();
  }

  public Date getExpirationDate(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();

    return claims.getExpiration();
  }

  public Optional<String> getUsernameFromRequest(HttpServletRequest request) {
    Optional<String> jwtToken = getJwtToken(request);
    if (jwtToken.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(getUsername(jwtToken.get()));
  }

  public Optional<String> getJwtToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length < 1) {
      return Optional.empty();
    }

    Cookie jwtCookie = null;
    for( Cookie cookie : cookies ) {
      if(( "jwtToken").equals( cookie.getName() ) ) {
        jwtCookie = cookie;
        break;
      }
    }

    if( jwtCookie == null || StringUtils.isEmpty( jwtCookie.getValue() ) ) {
      return Optional.empty();
    }

    return Optional.of(jwtCookie.getValue());
  }

  public boolean validate(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      //logger.error("Invalid JWT signature - {}", ex.getMessage());
    } catch (MalformedJwtException ex) {
      //logger.error("Invalid JWT token - {}", ex.getMessage());
    } catch (ExpiredJwtException ex) {
      //logger.error("Expired JWT token - {}", ex.getMessage());
    } catch (UnsupportedJwtException ex) {
      //logger.error("Unsupported JWT token - {}", ex.getMessage());
    } catch (IllegalArgumentException ex) {
      //logger.error("JWT claims string is empty - {}", ex.getMessage());
    }
    return false;
  }
}
