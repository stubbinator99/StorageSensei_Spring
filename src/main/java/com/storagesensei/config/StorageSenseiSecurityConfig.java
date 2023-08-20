package com.storagesensei.config;

import com.storagesensei.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class StorageSenseiSecurityConfig extends WebSecurityConfigurerAdapter {
  //@Autowired
  //private AuthenticationManager authenticationManager;

  /*@Autowired
  private DataSource dataSource;

	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

  @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
					.antMatchers("/", "/index", "/home", "/register", "/signup_form", "/register_success").permitAll()
					.anyRequest().authenticated()
			)
			.formLogin((form) -> form
					.loginPage("/login")
					.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

	@Bean
	public UserDetailsManager users() {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		return users;
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		//return authenticationConfiguration.getAuthenticationManager();
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService())
				.passwordEncoder(new BCryptPasswordEncoder())
				.and()
				.build();
	}

	// Replace this after getting it working?
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/

	@Autowired
	private DataSource dataSource;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DaoAuthenticationProvider daoAuthenticationProvider;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth//.authenticationProvider(daoAuthenticationProvider);
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
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
				.logout().logoutSuccessUrl("/").permitAll();
	}

	/*@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers("/resources/**", "/static/**");
	}*/








	/*@Autowired
	void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}*/

  /*@Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/anonymous*").anonymous()
        .antMatchers("/login*").permitAll()
        .antMatchers("/assets/css/**", "/assets/js/**", "/images/**").permitAll()
        .antMatchers("/index*").permitAll()
        //.antMatchers("/applications.html", "/releases.html", "/tickets.html").permitAll()//
        .antMatchers("/").permitAll()//
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/perform_login")
        .defaultSuccessUrl("/", false);



    *//*http
        .authorizeHttpRequests((authorize) -> {
          authorize
              .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
              .requestMatchers("/", "/index*", "/login*").permitAll()
              .anyRequest().authenticated()
              .and()
              .formLogin(form -> {
                form
                    .loginPage("/login*")
                    .loginProcessingUrl("/perform_login")
                    .defaultSuccessUrl(("/"), false);
              });
        });*//*
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource);

        //.parentAuthenticationManager(authenticationManager)
        *//*.inMemoryAuthentication();
  }*/

  // New way
  /*@Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authz) -> authz
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());
    return http.build();
  }*/












	/*@Autowired
	private DataSource dataSource;

	// Replace this after getting it working?
	*//*@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*//*

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
				.dataSource(dataSource)
				*//*.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, role from users where username=?")*//*
		;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login").permitAll()
				.and()
				.logout().permitAll();
	}*/
}
