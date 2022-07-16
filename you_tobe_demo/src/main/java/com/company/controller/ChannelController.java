package com.company.controller;

import com.company.dto.*;
import com.company.dto.channel.ChannelCreateDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.channel.ChannelShortInfoDTO;
import com.company.dto.channel.ChannelUpdateDTO;
import com.company.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @PostMapping("")
    public ResponseEntity<?> created(@RequestBody @Valid ChannelCreateDTO dto){

        ResponseInfoDTO created = channelService.created(dto);

        return ResponseEntity.ok(created);
    }

    @PutMapping("/{channelId}")
    public ResponseEntity<?> update(@PathVariable("channelId") String channelId, @RequestBody ChannelUpdateDTO dto){

        ChannelDTO update = channelService.update(channelId, dto);

        return ResponseEntity.ok(update);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> pagination(@RequestParam("page") Integer page , @RequestParam("size") Integer size){

        List<ChannelShortInfoDTO> pagination = channelService.pagination(page, size);

        return ResponseEntity.ok(pagination);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String channelId){

        ChannelDTO dto = channelService.getById(channelId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/adm/change_status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String channelId){

        ResponseInfoDTO dto = channelService.changeStatus(channelId);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/own_channel")
    public ResponseEntity<?> ownChannels(){

        List<ChannelShortInfoDTO> shortInfoDTOList = channelService.getAllByProfile();

        return ResponseEntity.ok(shortInfoDTOList);
    }
}
