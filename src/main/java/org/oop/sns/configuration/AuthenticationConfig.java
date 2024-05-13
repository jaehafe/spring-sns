package org.oop.sns.configuration;

import lombok.RequiredArgsConstructor;
import org.oop.sns.configuration.filter.JwtTokenFilter;
import org.oop.sns.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final UserService userService;
    @Value("${jwt.secret-key}")
    private String key;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .cors(cors -> cors
//                        .configurationSource(corsConfigurationSource())
//                )
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request -> request
                                .requestMatchers("/", "/api/*/users/*", "/oauth2/**").permitAll()
//                        .requestMatchers("/api/v1/user/**").hasRole("USER")
//                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                );
//                .addFilterBefore(new JwtTokenFilter(key, userService), UsernamePasswordAuthenticationFilter.class);
//                .httpBasic(withDefaults());

        return http.build();
    }
}
