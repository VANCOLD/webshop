package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * The TokenService class provides methods for generating and decoding JSON Web Tokens (JWTs),
 * as well as checking user privileges based on the decoded JWT.
 * It interacts with the JwtEncoder, UserService, and PrivilegeService to perform these operations.
 */
@Service("tokenService")
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final UserService userService;
    private final PrivilegeService privilegeService;

    /**
     * Constructs a new TokenService instance.
     *
     * @param jwtEncoder       The JwtEncoder for encoding JWTs.
     * @param userService      The UserService for managing user-related operations.
     * @param privilegeService The PrivilegeService for managing privileges.
     */
    public TokenService(JwtEncoder jwtEncoder, UserService userService, PrivilegeService privilegeService) {
        this.jwtEncoder         = jwtEncoder;
        this.userService        = userService;
        this.privilegeService   = privilegeService;
    }

    /**
     * Generates a JSON Web Token (JWT) for the provided authentication.
     *
     * @param authentication The authentication object containing user details and authorities.
     * @return The generated JWT as a string.
     */
    public String generateJwt(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .subject(authentication.getName())
            .claim("authority", scope)
            .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Decodes a JSON Web Token (JWT) and retrieves the associated user information.
     *
     * @param securityContext The security context containing the JWT.
     * @return The user associated with the decoded JWT.
     */
    public User decodeJwt(SecurityContext securityContext) {
        String username = ((Jwt) securityContext.getAuthentication().getPrincipal()).getSubject();
        return userService.findByUsername(username);
    }

    /**
     * Checks if the user associated with the decoded JWT has a specific privilege.
     *
     * @param privilegeName The name of the privilege to check.
     * @return True if the user has the privilege, false otherwise.
     */
    public boolean hasPrivilege(String privilegeName) {
        var user = decodeJwt(SecurityContextHolder.getContext());

        try {
            var privilegeObject = privilegeService.findByName(privilegeName);
            return user.getRole().getPrivileges().stream().anyMatch(privilege -> privilege.equals(privilegeObject));
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
