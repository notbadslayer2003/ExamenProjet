package com.stuttgartspeed.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final KeycloakAccessConverter keycloakAccessConverter = new KeycloakAccessConverter();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/cars").permitAll()
                        .requestMatchers("/custom/swagger-ui/**","/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cars/car", "/api/cars/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/cars/car").hasRole("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/cars/{id}").hasRole("admin")
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter grantedAuthoritiesConverter = new JwtAuthenticationConverter();
        grantedAuthoritiesConverter.setJwtGrantedAuthoritiesConverter(keycloakAccessConverter);
        return grantedAuthoritiesConverter;
    }

}
