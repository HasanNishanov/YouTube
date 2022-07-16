package com.company.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {

    private String password;
    private String email;

    private String jwt;
}
