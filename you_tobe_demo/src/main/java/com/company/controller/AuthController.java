package com.company.controller;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.profile.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.RegistrationDTO;
import com.company.service.AuthService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "Authorization and Registration")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Login", notes = "Method for login", nickname = "Some nick name")
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        log.info("Request for login dto: {}", dto);
        ProfileDTO profileDTO = authService.login(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Registration", notes = "Method for registration", nickname = "Some nick name")
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationDTO dto) {
        log.info("Request for registration dto: {}", dto);
        String profileDTO = authService.registration(dto);
        return ResponseEntity.ok(profileDTO);
    }


    @ApiOperation(value = "Verification email", notes = "Method for verification email", nickname = "Some nick name")
    @GetMapping("/email/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {

        log.info("Request for verification email");
        ResponseInfoDTO responseInfoDTO = authService.emailVerification(JwtUtil.decode(jwt));

        return ResponseEntity.ok(responseInfoDTO);
    }


    @ApiOperation(value = "Resend email", notes = "Method for resend email")
    @GetMapping("/resend/email/{jwt}")
    public ResponseEntity<?> resendEmail(@PathVariable("jwt") String jwt) {

        ResponseInfoDTO response = authService.resendEmail(JwtUtil.decode(jwt));
        log.info("Request for resend  email ");
        return ResponseEntity.ok(response);
    }
}
