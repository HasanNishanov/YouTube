package com.company.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangePasswordDTO {

    @NotNull
    @Size(min = 6)
    String password;
}
