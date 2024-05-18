package com.hong.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.sql.DataSource;

import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

// import org.springframework.boot.CommandLineRunner;

@Configuration
public class SecurityConfig {

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Autowired
    @Qualifier("customAccessDeniedHandler")
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

		UserDetails hong  = builder.username("hong").password("hong").roles("USER").build();
		UserDetails admin = builder.username("admin").password("admin").roles("ADMIN").build();
        UserDetails boss  = builder.username("bigboss").password("bigboss").roles("USER", "ADMIN").build();

		userManager.createUser(hong);
        userManager.createUser(admin);
        userManager.createUser(boss);
	}


    @Bean
    UserDetailsService userDetailsService(DataSource dataSource) {
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
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                // .anyRequest().authenticated()
                .requestMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/books/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/books/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/books/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
                .anyRequest().denyAll()
            )
            // .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // This so embedded frames in h2-console are working
            // .httpBasic(Customizer.withDefaults());

            .httpBasic(basic -> basic.authenticationEntryPoint(authEntryPoint))
            .exceptionHandling(customizer -> customizer.accessDeniedHandler(accessDeniedHandler))
            ;
        return http.build();
    }

}
