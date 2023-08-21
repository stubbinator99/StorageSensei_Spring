package com.storagesensei.config;

import com.storagesensei.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private DaoAuthenticationProvider daoAuthenticationProvider;

	private BCryptPasswordEncoder passwordEncoder;
	private UserDetailsService userDetailsService;
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	private final JwtTokenFilter jwtTokenFilter;

	public SecurityConfig(/*UserRepo userRepo,*/ JwtTokenFilter jwtTokenFilter) {
		//this.userRepo = userRepo;
		this.jwtTokenFilter = jwtTokenFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth//.authenticationProvider(daoAuthenticationProvider);
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.authorizeRequests()
				.antMatchers("/", "/index", "/home", "/register", "/signup_form", "/register_user", "/register_success").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")//
				//.usernameParameter("email")
				.defaultSuccessUrl("/")
				//.successHandler(authenticationSuccessHandler)
				.permitAll()
				.and()
				.logout().logoutSuccessUrl("/").permitAll();*/

		// Enable CORS and disable CSRF
		http = http.cors().and().csrf().disable();

		// Set session management to stateless
		http = http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and();

		// Set unauthorized requests exception handler
		http = http
				.exceptionHandling()
				.authenticationEntryPoint(
						(request, response, ex) -> {
							response.sendError(
									HttpServletResponse.SC_UNAUTHORIZED,
									ex.getMessage()
							);
						}
				)
				.and();

		// Set permissions on endpoints
		http.authorizeRequests()
				// Our public endpoints
				.antMatchers("/", "/index", "/home", "/register", "/signup_form", "/register_user", "/register_success").permitAll()
				.antMatchers("/login").permitAll()
				//.antMatchers("/api/public/**").permitAll()
				//.antMatchers(HttpMethod.GET, "/api/author/**").permitAll()
				//.antMatchers(HttpMethod.POST, "/api/author/search").permitAll()
				//.antMatchers(HttpMethod.GET, "/api/book/**").permitAll()
				//.antMatchers(HttpMethod.POST, "/api/book/search").permitAll()
				// Our private endpoints
				.anyRequest().authenticated();
				// The below code is an addition not in the tutorial
				/*.and()
				.httpBasic(Customizer.withDefaults())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);*/

		// Add JWT token filter before the Spring Security filters
		http.addFilterBefore(
				jwtTokenFilter,
				UsernamePasswordAuthenticationFilter.class
		);
	}

	/**
	 * Beans
	 */

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		if (passwordEncoder == null) {
			passwordEncoder = new BCryptPasswordEncoder();
		}

		return passwordEncoder;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		if (userDetailsService == null) {
			userDetailsService = new CustomUserDetailsService();
		}

		return userDetailsService;
	}

	@Bean
	public AuthenticationSuccessHandler appAuthenticationSuccessHandler(){
		if (authenticationSuccessHandler == null) {
			authenticationSuccessHandler = new CustomAuthenticationSuccessHandler();
		}

		return authenticationSuccessHandler;
	}

	// Used by Spring Security if CORS is enabled.
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source =
				new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	// Expose the AuthenticationManager Bean to make it public
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
}
