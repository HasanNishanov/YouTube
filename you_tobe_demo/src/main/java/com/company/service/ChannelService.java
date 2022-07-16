package com.company.service;

import com.company.dto.*;
import com.company.dto.channel.*;
import com.company.entity.AttachEntity;
import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChannelStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;


    public ResponseInfoDTO created(ChannelCreateDTO dto) {

        ChannelEntity channel = new ChannelEntity();
        channel.setName(dto.getName());
        channel.setProfileId(profileService.getProfile().getId());
        if (dto.getAttach() != null) {
            channel.setAttachId(dto.getAttach());
        }

        if (dto.getBanner() != null) {
            channel.setBannerId(dto.getBanner());
        }

        if (dto.getInstagramUrl() != null) {
            channel.setInstagramUrl(dto.getInstagramUrl());
        }

        if (dto.getTelegramUrl() != null) {
            channel.setTelegramUrl(dto.getTelegramUrl());
        }

        if (dto.getWebsiteUrl() != null) {
            channel.setWebsiteUrl(dto.getWebsiteUrl());
        }

        channelRepository.save(channel);

        return new ResponseInfoDTO(1, "success");
    }

    public ChannelDTO update(String channelId, ChannelUpdateDTO dto) {

        ChannelEntity channel = get(channelId);

        ProfileEntity profile = profileService.getProfile();

        if (!channel.getProfile().getId().equals(profile.getId())) {
            throw new BadRequestException("no access");
        }

        AttachEntity oldBanner = channel.getBanner();
        if (channel.getBannerId() == null && dto.getBanner() != null) {
            channel.setBannerId(dto.getBanner());
        } else if (channel.getBannerId() != null && dto.getBanner() != null) {
            channel.setBannerId(dto.getBanner());
        } else if (channel.getBannerId() != null && dto.getBanner() == null) {
            channel.setBannerId(null);
        }

        AttachEntity oldPhoto = channel.getAttach();
        if (channel.getAttachId() == null && dto.getAttach() != null) {
            channel.setAttachId(dto.getAttach());
        } else if (channel.getAttachId() != null && dto.getAttach() != null) {
            channel.setAttachId(dto.getAttach());
        } else if (channel.getAttachId() != null && dto.getAttach() == null) {
            channel.setAttachId(null);
        }

        channel.setName(dto.getName());
        channel.setStatus(dto.getStatus());
        channel.setWebsiteUrl(dto.getWebsiteUrl());
        channel.setTelegramUrl(dto.getTelegramUrl());
        channel.setInstagramUrl(dto.getInstagramUrl());

        channelRepository.save(channel);

        if (oldBanner != null){
            attachService.deleted(oldBanner.getUuid());
        }

        if (oldPhoto != null){
            attachService.deleted(oldPhoto.getUuid());
        }

        return getChannelDTO(channel);
    }

    private ChannelDTO getChannelDTO(ChannelEntity channel) {

        ChannelDTO dto = new ChannelDTO();
        dto.setUuid(channel.getUuid());
        dto.setAttachUrl(attachService.getAttachOpenUrl(channel.getAttachId()));
        dto.setBannerUrl(attachService.getAttachOpenUrl(channel.getBannerId()));
        dto.setInstagramUrl(channel.getInstagramUrl());
        dto.setTelegramUrl(channel.getTelegramUrl());
        dto.setWebsiteUrl(channel.getWebsiteUrl());
        dto.setStatus(channel.getStatus());
        dto.setCreatedDate(channel.getCreatedDate());
        dto.setVisible(channel.getVisible());
        dto.setProfile(profileService.getProfileDTOById(channel.getProfileId()));

        return dto;
    }

    private ChannelEntity get(String channelId) {

        return channelRepository.findById(channelId).orElseThrow(() -> {
            throw new ItemNotFoundException("channel not fount");
        });
    }

    public List<ChannelShortInfoDTO> pagination(Integer page, Integer size) {

        List<ChannelShortDTO> pagination = channelRepository.pagination(size, page * size);

        List<ChannelShortInfoDTO> shortInfoDTOS = new ArrayList<>();
        pagination.forEach(channel -> {

            ChannelShortInfoDTO dto = new ChannelShortInfoDTO();
            dto.setAttachUrl(attachService.getAttachOpenUrl(channel.getPhoto()));
            dto.setName(channel.getName());
            System.out.println(channel.getPId());
            dto.setProfile(profileService.getShortInfo(channel.getPId()));
            dto.setStatus(channel.getStatus());
            dto.setUuid(channel.getUuid());
            dto.setCreatedDate(channel.getCreatedDate());
            dto.setVisible(channel.getVisible());

            shortInfoDTOS.add(dto);
        });

        return shortInfoDTOS;
    }

    private ChannelShortInfoDTO getShortInfo(ChannelEntity channelEntity) {

        ChannelShortInfoDTO dto = new ChannelShortInfoDTO();
        dto.setName(channelEntity.getName());
        dto.setProfile(profileService.getShortInfo(channelEntity.getProfileId()));
        dto.setAttachUrl(attachService.getAttachOpenUrl(channelEntity.getAttachId()));
        dto.setStatus(channelEntity.getStatus());
        dto.setUuid(channelEntity.getUuid());
        dto.setVisible(channelEntity.getVisible());
        dto.setCreatedDate(channelEntity.getCreatedDate());

        return dto;
    }

    public ChannelDTO getById(String channelId) {

        ChannelEntity channel = get(channelId);
        return getChannelDTO(channel);
    }

    public ResponseInfoDTO changeStatus(String channelId) {

        ChannelEntity channel = get(channelId);
        channel.setStatus(channel.getStatus().equals(ChannelStatus.ACTIVE) ?
                ChannelStatus.BLOCKED : ChannelStatus.ACTIVE);

        channelRepository.save(channel);

        return new ResponseInfoDTO(1,"success");
    }

    public List<ChannelShortInfoDTO> getAllByProfile() {

        ProfileEntity profile = profileService.getProfile();

        List<ChannelEntity> list = channelRepository.findAllByVisibleAndProfileId(Boolean.TRUE, profile.getId());

        List<ChannelShortInfoDTO> shortInfoDTOS = new ArrayList<>();

        list.forEach(channelEntity -> {
            shortInfoDTOS.add(getShortInfo(channelEntity));
        });

        return shortInfoDTOS;
    }
}
