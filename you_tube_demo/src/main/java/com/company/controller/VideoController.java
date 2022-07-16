package com.company.controller;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.video.VideoCreatedDTO;
import com.company.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("")
    public ResponseEntity<?> created(@RequestBody @Valid VideoCreatedDTO dto){

        ResponseInfoDTO dto1 = videoService.createdVideo(dto);

        return ResponseEntity.ok(dto1);
    }


}
