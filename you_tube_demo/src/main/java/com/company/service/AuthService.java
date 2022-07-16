package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.ResponseInfoDTO;
import com.company.dto.profile.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.RegistrationDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public ProfileDTO login(AuthDTO authDTO) {

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();

        ProfileEntity profile = user.getProfile();
        ProfileDTO dto = new ProfileDTO();
        dto.setJwt(JwtUtil.encode(profile.getId()));

        return dto;
    }

    public String registration(RegistrationDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setUsername(dto.getUsername());

        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        profileRepository.save(entity);

        //       smsService.sendRegistrationSms(dto.getPhone());

        emailService.sendRegistrationEmail(entity.getEmail(), entity);
        // name; surname; email; password;

        return "Message sending";
    }

//    public String encode(String password) {
//
////        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
////
////        return b.encode(password);
//
//    }

    public ResponseInfoDTO emailVerification(Integer id) {

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseInfoDTO(-1, "<h1>User Not Found</h1>");
        }

        ProfileEntity profile = optional.get();
        if (!emailService.verificationTime(profile.getEmail())) {
            return new ResponseInfoDTO(-1, "<h1>Time is out</h1>");

        }

        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);
        return new ResponseInfoDTO(1, "<h1 style='align-text:center'>Success. Tabriklaymiz.</h1>");
    }

    public ResponseInfoDTO resendEmail(Integer id) {

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("User not fount");
        }
        ProfileEntity profile = optional.get();

        Long count = emailService.countVerivifationSending(profile.getEmail());
        if (count >= 4) {
            return new ResponseInfoDTO(-1, "limit");
        }

        emailService.sendRegistrationEmail(profile.getEmail(), profile);
        return new ResponseInfoDTO(1, "success");
    }
}
