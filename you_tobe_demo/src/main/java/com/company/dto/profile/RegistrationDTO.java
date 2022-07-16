package com.company.dto.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegistrationDTO {

    //   @ApiModelProperty(name = "get qilish", example = "masalan")
    private String password;
    private String username;
    private String email;

    // @ApiModelProperty(name = "get qilish", example = "masalan")
}
