package com.prozacto.prozacto.model.enums;

import lombok.Getter;

@Getter
public enum  UserType {
    PATIENT(1),
    DOCTOR(2),
    ADMIN(3);

    private final int id;

    UserType(int id) {
        this.id = id;
    }
}
