package com.prozacto.prozacto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LocationDto {
    private Integer id;
    private Double latitude;
    private Double longitude;
    private String landmark;
    private String address;
}
