package com.hong.demo.config;

// import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.sql.DataSource;


@Configuration
public class WebSecurityConfig {

    // @Autowired
    // @Qualifier("customAuthenticationEntryPoint")
    // private AuthenticationEntryPoint authEntryPoint;

    // @Autowired
    // @Qualifier("customAccessDeniedHandler")
    // private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    private AuthenticationEntryPoint authEntryPoint;

    @Autowired
    @Qualifier("delegatedAccessDeniedHandler")
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public UserDetailsService userDetailsService(){

    //     UserDetails hong = User.builder()
    //             .username("hong")
    //             .password(passwordEncoder().encode("password"))
    //             .roles("USER")
    //             .build();

    //     UserDetails admin = User.builder()
    //             .username("admin")
    //             .password(passwordEncoder().encode("admin"))
    //             .roles("ADMIN")
    //             .build();

    //     return new InMemoryUserDetailsManager(hong, admin);
    // }

    private void usersInit(UserDetailsManager userManager){
		// users init ----
		User.UserBuilder builder = User.builder().passwordEncoder(passwordEncoder()::encode);

		UserDetails hong = builder
        .username("hong")
        .password("hong")
        .roles("USER")
        .build();

        UserDetails autor = builder
        .username("autor")
        .password("autor")
        .roles("AUTOR")
        .build();

		UserDetails admin = builder
        .username("admin")
        .password("admin")
        .roles("USER", "AUTOR", "ADMIN")
        .build();

		userManager.createUser(hong);
        userManager.createUser(autor);
        userManager.createUser(admin);
	}

    @Bean
    UserDetailsService userService(DataSource dataSource) {
        // return new JdbcUserDetailsManager(dataSource);
        UserDetailsManager userManager = new JdbcUserDetailsManager(dataSource);

        // User.UserBuilder builder  = User.builder().passwordEncoder(passwordEncoder()::encode);
        
        // var hong = builder
        //         .username("hong")
        //         .password("hong")
        //         .roles("USER")
        //         .build();

        // var admin = builder
        //         .username("admin")
        //         .password("admin")
        //         .roles("ADMIN")
        //         .build();

        // var boss = builder
        //         .username("bigboss")
        //         .password("bigboss")
        //         .roles("USER", "ADMIN")
        //         .build();

        // userManager.createUser(hong);
        // userManager.createUser(admin);
        // userManager.createUser(boss);

        usersInit(userManager);
        return userManager;
    }

    @Bean
    @Order(1)
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .securityMatcher("/api/**")
            .authorizeHttpRequests(authorize -> authorize
                // .anyRequest().authenticated()
                // .requestMatchers(HttpMethod.GET, "/h2-console/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()

                // .requestMatchers("/api/books/**").hasRole("AUTOR")
                .requestMatchers(HttpMethod.POST, "/api/books/**").hasRole("AUTOR")
                .requestMatchers(HttpMethod.PUT, "/api/books/**").hasRole("AUTOR")
                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")


                // .requestMatchers("/api/reviews/**").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/reviews/**").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/api/reviews/**").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")

                .anyRequest().denyAll()
            )
            // .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // This so embedded frames in h2-console are working
            
            // .httpBasic(Customizer.withDefaults())
            
            .httpBasic(basic -> basic.authenticationEntryPoint(authEntryPoint))
            .exceptionHandling(customizer -> customizer.accessDeniedHandler(accessDeniedHandler))
            ;
        return http.build();
    }

    @Bean                                                            
	public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(Customizer.withDefaults());
		return http.build();
	}

}
