package com.storagesensei.config;

import com.storagesensei.db.CustomUserDetails;
import com.storagesensei.db.UserRepository;
import com.storagesensei.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
  private final JwtTokenUtil jwtTokenUtil;
  private final UserRepository userRepo;

  public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,
                        UserRepository userRepo) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.userRepo = userRepo;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    /*// Get authorization header and validate
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (isEmpty(header) || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Get jwt token and validate
    final String token = header.split(" ")[1].trim(); // header is 'Basic {credentials/token}'
    //final String token2 = header;// Should this be done instead?*/

    // Get the token instead of using the Http Authorization Header
    Optional<String> jwtTokenOptional = jwtTokenUtil.getJwtToken(request);

    if (jwtTokenOptional.isEmpty()) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwtToken = jwtTokenOptional.get();
    if (!jwtTokenUtil.validate(jwtToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    // Get user identity and set it on the spring security context
    /*UserDetails userDetails = userRepo
        .findByUsername(jwtTokenUtil.getUsername(token))
        .orElse(null);*/
    User user = userRepo
        .findByUsername(jwtTokenUtil.getUsername(jwtToken))
        .orElse(null);
    UserDetails userDetails = user == null ? null : new CustomUserDetails(user);

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails == null ? List.of() : userDetails.getAuthorities()
    );

    authentication.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request)
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
