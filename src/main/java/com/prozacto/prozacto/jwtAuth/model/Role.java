package com.prozacto.prozacto.jwtAuth.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"),
    DOCTOR("USER"),
    PATIENT("PATIENT");

    String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getAuthority() {
        return roleName;
    }

}