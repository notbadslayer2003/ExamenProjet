package com.stuttgartspeed.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KeycloakAccessConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        Map<String, Object> resourceAccess = (Map<String, Object>) jwt.getClaims().get("resource_access");
        if (resourceAccess != null && resourceAccess.containsKey("springbootapp")) {
            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("springbootapp");
            if (clientAccess != null && clientAccess.containsKey("roles")) {
                List<String> clientRoles = (List<String>) clientAccess.get("roles");
                for (String c : clientRoles) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + c));
                }
            }
        }
        return authorities;
    }

}
