package com.openclassrooms.mddapi.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.openclassrooms.mddapi.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Configuration class for Spring Security.
 *
 * Defines security rules, JWT filters, password encoding, and token handling.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtKey;

    private final CustomUserDetailsService userDetailsService;

    /**
     * Defines the security filter chain with stateless session management,
     * JWT filter, and endpoint authorization rules.
     *
     * @param http the HTTP security configuration
     * @param jwtAuthenticationFilter the custom JWT authentication filter
     * @return the configured SecurityFilterChain
     * @throws Exception in case of configuration error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Provides a BCryptPasswordEncoder for password hashing.
     *
     * @return the password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a JwtDecoder using the configured secret key.
     *
     * @return the JWT decoder
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(jwtKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    /**
     * Creates a JwtEncoder using the configured secret key.
     *
     * @return the JWT encoder
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
    }

    /**
     * Creates and injects the custom JWT authentication filter.
     *
     * @param jwtEncoder the JWT encoder
     * @param jwtDecoder the JWT decoder
     * @return the authentication filter
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        return new JwtAuthenticationFilter(jwtUtil(jwtEncoder, jwtDecoder), userDetailsService);
    }

    /**
     * Creates a JwtUtil instance used to handle token creation and parsing.
     *
     * @param jwtEncoder the JWT encoder
     * @param jwtDecoder the JWT decoder
     * @return the JwtUtil instance
     */
    @Bean
    public JwtUtil jwtUtil(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        return new JwtUtil(jwtEncoder, jwtDecoder);
    }

    /**
     * Provides the authentication manager used by Spring Security.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the AuthenticationManager
     * @throws Exception in case of error
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
