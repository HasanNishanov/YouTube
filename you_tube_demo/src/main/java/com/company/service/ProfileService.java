package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.profile.ChangePasswordDTO;
import com.company.dto.ResponseInfoDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileFilterDTO;
import com.company.dto.profile.ProfileShortDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.repository.custom.CustomProfileRepository;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import com.company.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private CustomProfileRepository customProfileRepository;
    @Autowired
    private EmailService emailService;

    public String encode(String password) {

        BCryptPasswordEncoder b = new BCryptPasswordEncoder();

        return b.encode(password);
    }

    // admin
    public ProfileDTO create(ProfileDTO dto) {
        // name; surname; login; password;
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(encode(dto.getPassword()));
        entity.setUsername(dto.getUsername());

        entity.setRole(dto.getRole());
        entity.setStatus(ProfileStatus.ACTIVE);

        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setJwt(JwtUtil.encode(entity.getId()));
        return dto;
    }

    public void update(ProfileDTO dto) {

        ProfileEntity entity = getProfile();

        if (entity.getPhoto() == null && dto.getPhotoId() != null) {

            entity.setPhoto(new AttachEntity(dto.getPhotoId()));

        } else if (entity.getPhoto() != null && dto.getPhotoId() == null) {

            attachService.deleted(entity.getPhoto().getUuid());
            entity.setPhoto(null);

        } else if (entity.getPhoto() != null && dto.getPhotoId() != null &&
                entity.getPhoto().getUuid().equals(dto.getPhotoId())) {

            entity.setPhoto(new AttachEntity(dto.getPhotoId()));

        }

        entity.setUsername(dto.getUsername());

        profileRepository.save(entity);

    }

    public void update(Integer profileId, ProfileDTO dto) {

        ProfileEntity entity = get(profileId);

        if (entity.getPhoto() == null && dto.getPhotoId() != null) {

            entity.setPhoto(new AttachEntity(dto.getPhotoId()));

        } else if (entity.getPhoto() != null && dto.getPhotoId() == null) {

            attachService.deleted(entity.getPhoto().getUuid());
            entity.setPhoto(null);

        } else if (entity.getPhoto() != null && dto.getPhotoId() != null &&
                entity.getPhoto().getUuid().equals(dto.getPhotoId())) {

            entity.setPhoto(new AttachEntity(dto.getPhotoId()));

        }
        entity.setUsername(dto.getUsername());
        profileRepository.save(entity);

    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profile Not found");
        });
    }

    public ProfileDTO getProfileDTOById(Integer id) {

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("This user not fount");
        }

        ProfileEntity profileEntity = optional.get();
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setCreatedDate(profileEntity.getCreatedDate());
        profileDTO.setUsername(profileEntity.getUsername());
        profileDTO.setId(profileEntity.getId());

        return profileDTO;
    }

    public List<ProfileDTO> getAllProfileDTOById() {

        Iterable<ProfileEntity> iterable = profileRepository.findAll();

        List<ProfileDTO> profileDTOS = new ArrayList<>();
        iterable.forEach(profileEntity -> {

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setEmail(profileEntity.getEmail());
            profileDTO.setCreatedDate(profileEntity.getCreatedDate());
            profileDTO.setUsername(profileEntity.getUsername());
            profileDTO.setId(profileEntity.getId());

            profileDTOS.add(profileDTO);
        });

        return profileDTOS;
    }

    public ProfileDTO changeVisible(Integer profileId) {

        ProfileEntity entity = get(profileId);
        entity.setVisible(!entity.getVisible());

        profileRepository.save(entity);

        return getProfileDTOById(entity.getId());
    }

    public void save(ProfileEntity profile) {
        profileRepository.save(profile);
    }

    public List<ProfileShortDTO> filter(ProfileFilterDTO dto) {
        List<ProfileEntity> list = customProfileRepository.filter(dto);

        List<ProfileShortDTO> profileShortList = new ArrayList<>();
        list.forEach(entity -> {
            profileShortList.add(new ProfileShortDTO
                    (entity.getId(), entity.getUsername(), entity.getEmail(), entity.getRole()));
        });

        return profileShortList;
    }

    public ProfileEntity getProfileDTOByEmail(String email) {

        return profileRepository.findByEmail(email).orElseThrow(() -> {
            throw new ItemNotFoundException("User not fount");
        });
    }

    public ProfileEntity getProfile() {

        CustomUserDetails user = SpringSecurityUtil.getCurrentUser();
        return user.getProfile();
    }

    public ResponseInfoDTO changePassword(ChangePasswordDTO dto) {

        ProfileEntity profile = getProfile();
        profile.setPassword(MD5Util.getMd5(dto.getPassword()));
        profileRepository.save(profile);

        return new ResponseInfoDTO(1, "success");

    }

    public ResponseInfoDTO changeEmail(ChangePasswordDTO dto) {

        ProfileEntity profile = getProfile();
        profile.setTempEmail(dto.getPassword());
//        profile.setStatus(ProfileStatus.NOT_ACTIVE);
        profileRepository.save(profile);

        emailService.sendRegistrationEmail(dto.getPassword(),profile);

        return new ResponseInfoDTO(1, "success");
    }

    public ProfileShortDTO getShortInfo(Integer profileId) {

        ProfileEntity profile = get(profileId);

        ProfileShortDTO dto = new ProfileShortDTO();
        dto.setEmail(profile.getEmail());
        dto.setId(profile.getId());
        dto.setRole(profile.getRole());
        dto.setUsername(profile.getUsername());

        return dto;
    }
}
