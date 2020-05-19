package com.prozacto.prozacto.model;

import lombok.Getter;

@Getter
public enum  SubSpecializationEnum {
    ASSISTANT_DOCTOR(1),
    NURSE(2),
    GENERAL_CONSULTANT(3),
    CHEMIST(4),
    SURGEON(5),
    OTHERS(6);

    private final int id;

    SubSpecializationEnum(int id) {
        this.id = id;
    }
}
