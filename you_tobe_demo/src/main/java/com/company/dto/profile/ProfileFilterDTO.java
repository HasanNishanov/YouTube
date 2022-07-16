package com.company.dto.profile;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileFilterDTO {

    private Integer id;
    private String username;
    private String email;
    private String password;
    private String createdDateFrom;
    private String createdDateTo;
    private Boolean visible;
    private ProfileStatus status;
    private ProfileRole role;

}
