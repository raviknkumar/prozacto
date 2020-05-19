package com.prozacto.prozacto.model.enums;

import lombok.Getter;

@Getter
public enum AppointmentStatusEnum {

    PENDING(0),
    IN_PROGRESS(1),
    COMPLETED(2),
    MISSED(3),
    RESCHEDULED(4);

    private final int status;

    AppointmentStatusEnum(int status) {
        this.status = status;
    }
}
