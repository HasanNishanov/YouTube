package com.company.controller;

import com.company.dto.profile.ChangePasswordDTO;
import com.company.dto.ResponseInfoDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileFilterDTO;
import com.company.dto.profile.ProfileShortDTO;
import com.company.service.AttachService;
import com.company.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(value = "Profile Controller")
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    @ApiOperation(value = "profile create by admin")
    @PostMapping("/adm")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto) {

        ProfileDTO profileDTO = profileService.create(dto);
        log.info("Request profile create by admin dto:{}", dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "profile update by admin")
    @PutMapping("/adm/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") Integer id,
                                             @RequestBody ProfileDTO dto) {
        profileService.update(id, dto);
        log.info("Request profile update by admin dto:{}", dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "profile update by profile")
    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> detailUpdate(@RequestBody ProfileDTO dto) {
        profileService.update(dto);
        log.info("Request profile update by profile dto:{}", dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "profile get by admin")
    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getProfile(@PathVariable("id") Integer id) {

        ProfileDTO profileDTO = profileService.getProfileDTOById(id);
        log.info("Request profile get by admin profileId:{}", id);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "profile list by admin")
    @GetMapping("/adm")
    public ResponseEntity<?> getProfileList() {

        List<ProfileDTO> profileDTOS = profileService.getAllProfileDTOById();
        log.info("Request profile list by admin ");
        return ResponseEntity.ok(profileDTOS);
    }

    @ApiOperation(value = "profile change visible by admin")
    @DeleteMapping("/adm")
    public ResponseEntity<?> changeVisible(@RequestParam("id") Integer profileId) {

        ProfileDTO profileDTO = profileService.changeVisible(profileId);
        log.info("Request profile change visible by admin profileId:{}", profileId);
        return ResponseEntity.ok(profileDTO);

    }


    @ApiOperation(value = "profile filter by admin")
    @PostMapping("/adm/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO dto) {
        log.info("Request profile filter by admin ");
        List<ProfileShortDTO> response = profileService.filter(dto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/change_pass")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDTO dto){

        ResponseInfoDTO dto1 = profileService.changePassword(dto);

        return ResponseEntity.ok(dto1);

    }

    @PostMapping("/change_email")
    public ResponseEntity<?> changeEmail(@RequestBody @Valid ChangePasswordDTO dto){

        ResponseInfoDTO dto1 = profileService.changeEmail(dto);

        return ResponseEntity.ok(dto1);
    }
    // filter**********
}
