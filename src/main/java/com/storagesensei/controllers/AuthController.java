package com.storagesensei.controllers;

import com.storagesensei.config.AuthRequest;
import com.storagesensei.config.JwtTokenUtil;
import com.storagesensei.config.UserView;
import com.storagesensei.config.UserViewMapper;
import com.storagesensei.db.CustomUserDetails;
import com.storagesensei.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
//@RequestMapping(path = "/api/public")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  //private final UserViewMapper userViewMapper;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager,
                 JwtTokenUtil jwtTokenUtil/*,
                 UserViewMapper userViewMapper*/) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    //this.userViewMapper = userViewMapper;
    }

  @PostMapping("/login")
  public /*ResponseEntity<UserView>*/ ResponseEntity<String> login(/*@Request @Valid AuthRequest request*/
      @RequestParam("username") final String username,
      @RequestParam("password") final String password,
      HttpServletResponse response
  ) {
    AuthRequest request = new AuthRequest(username, password);
    try {
      Authentication authenticate = authenticationManager
          .authenticate(
              new UsernamePasswordAuthenticationToken(
                  request.getUsername(), request.getPassword()
              )
          );

      CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();//User user = (User) authenticate.getPrincipal();

      String jwtToken = jwtTokenUtil.generateAccessToken(user);
      Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
      jwtCookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(7));
      jwtCookie.setSecure(true);
      jwtCookie.setHttpOnly(true);
      response.addCookie(jwtCookie);

      return ResponseEntity.ok()
          .header(
              HttpHeaders.AUTHORIZATION,
              jwtToken
          )
          //.body(userViewMapper.toUserView(user));
          .body("login successful");
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
