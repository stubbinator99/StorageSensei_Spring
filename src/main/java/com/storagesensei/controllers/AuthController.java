package com.storagesensei.controllers;

import com.storagesensei.config.AuthRequest;
import com.storagesensei.config.JwtTokenUtil;
import com.storagesensei.config.UserView;
import com.storagesensei.config.UserViewMapper;
import com.storagesensei.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/public")
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
  public ResponseEntity<UserView> login(@RequestBody @Valid AuthRequest request) {
    try {
      Authentication authenticate = authenticationManager
          .authenticate(
              new UsernamePasswordAuthenticationToken(
                  request.getUsername(), request.getPassword()
              )
          );

      User user = (User) authenticate.getPrincipal();

      return ResponseEntity.ok()
          .header(
              HttpHeaders.AUTHORIZATION,
              jwtTokenUtil.generateAccessToken(user)
          )
          .body(userViewMapper.toUserView(user));
          //.body("");
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
