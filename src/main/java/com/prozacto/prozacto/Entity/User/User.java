
package com.prozacto.prozacto.Entity.User;

import com.prozacto.prozacto.Entity.BaseEntityIntID;
import com.prozacto.prozacto.jwtAuth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Where(clause = "deleted = 0")
public class User extends BaseEntityIntID {

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "age")
    private Integer age;

    @Column(name = "userType")
    private Integer userType;

    private transient List<String> rolesList;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
}