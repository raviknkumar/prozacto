package com.prozacto.prozacto.Entity.Location;

import com.prozacto.prozacto.Entity.BaseEntityIntID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "location")
@Where(clause = "deleted = 0")
public class Location extends BaseEntityIntID {

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "address")
    private String address;
}
