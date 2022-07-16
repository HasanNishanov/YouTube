package com.company.dto.profile;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {

    private Integer id;
    private String username;

    private String password;
    @Email(message = "Email should be valid", regexp = "[a-zA-Z]+[0-9]+@[a-z]+\\.[a-zA-Z]+")
    private String email;

    private LocalDateTime createdDate;
    private Boolean visible;
    private ProfileStatus status;
    private ProfileRole role;

    private String photoId;

    private String jwt;

}
