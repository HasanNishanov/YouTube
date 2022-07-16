package com.company.controller;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.playlist.PlaylistCreatedDTO;
import com.company.dto.playlist.PlaylistDTO;
import com.company.dto.playlist.PlaylistUpdateDTO;
import com.company.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping("")
    public ResponseEntity<?> created(@RequestBody PlaylistCreatedDTO dto) {
        PlaylistDTO created = playlistService.created(dto);

        return ResponseEntity.ok(created);
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<?> update(@RequestBody PlaylistUpdateDTO dto,
                                    @PathVariable("playlistId") String playlistId) {

        PlaylistDTO update = playlistService.update(dto, playlistId);

        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<?> changeVisible(@PathVariable("playlistId") String pId) {

        ResponseInfoDTO dto = playlistService.changeVisible(pId);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> pagination(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {

        List<PlaylistDTO> pagination = playlistService.pagination(page, size);

        return ResponseEntity.ok(pagination);
    }


    @GetMapping("/profile")
    public ResponseEntity<?> getProfilePlayList() {

//        ResponseInfoDTO dto = playlistService.changeVisible(pId);

//        return ResponseEntity.ok(dto);\
        return null;
    }


}
