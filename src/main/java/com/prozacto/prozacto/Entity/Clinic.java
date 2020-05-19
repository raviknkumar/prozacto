package com.prozacto.prozacto.Entity;

import com.prozacto.prozacto.Entity.Location.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clinic")
@Where(clause = "deleted = 0")
public class Clinic extends BaseEntityIntID {

    @Column(name = "name")
    private String name;

    private Integer locationId;
}
