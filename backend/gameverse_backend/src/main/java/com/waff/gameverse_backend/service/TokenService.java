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

@Service("tokenService")
public class TokenService {


    private final JwtEncoder jwtEncoder;

    private final UserService userService;

    private final PrivilegeService privilegeService;

    public TokenService(JwtEncoder jwtEncoder, UserService userService, PrivilegeService privilegeService) {
        this.jwtEncoder         = jwtEncoder;
        this.userService        = userService;
        this.privilegeService   = privilegeService;
    }


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

    public User decodeJwt(SecurityContext securityContext) {
        String username = ((Jwt)securityContext.getAuthentication().getPrincipal()).getSubject();
        return userService.findByUsername(username);
    }

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
