package com.prozacto.prozacto.model;

import lombok.Getter;

@Getter
public enum  SpecializationEnum {
    PHYSICIAN(1),
    CARDIOLOGIST(2),
    DEMITOLOGIST(3),
    NUEROLOGIST(4),
    NEPHROLOGIST(5),
    ORTHOPEDIC(6),
    ENT(7);

    private final int id;

    SpecializationEnum(int id) {
        this.id = id;
    }
}
