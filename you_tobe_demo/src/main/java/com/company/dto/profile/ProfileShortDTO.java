package com.company.dto.profile;

import com.company.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileShortDTO {

    private Integer id;
    private String username;
    private String email;
    private ProfileRole role;
}
