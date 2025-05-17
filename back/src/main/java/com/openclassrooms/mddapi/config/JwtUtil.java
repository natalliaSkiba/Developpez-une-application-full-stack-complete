package com.openclassrooms.mddapi.config;

import com.openclassrooms.mddapi.exception.InvalidTokenException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for generating and validating JWT tokens.
 *
 * Uses Spring Security's JwtEncoder and JwtDecoder to work with signed tokens.
 */
@AllArgsConstructor
public class JwtUtil {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;


    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param authentication the authenticated user
     * @return the generated JWT token as a String
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }

    /**
     * Extracts the username (subject) from the provided JWT token.
     *
     * @param token the JWT token to decode
     * @return the username (subject) from the token
     * @throws InvalidTokenException if the token is invalid or expired
     */
    public String extractUsername(String token) {
        try {
            Jwt decodedJwt = jwtDecoder.decode(token);

            if (decodedJwt.getSubject() == null || decodedJwt.getSubject().isEmpty()) {
                throw new InvalidTokenException("Token does not contain a valid subject");
            }
            return decodedJwt.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid or expired token");
        }
    }
}

