package com.prozacto.prozacto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserRequestDto {
    Integer id;
    String username;
    String contactNumber;
    String email;
}
