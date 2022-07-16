package com.company.service;

import com.company.entity.PlaylistVideoEntity;
import com.company.repository.PlaylistVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistVideoService {

    @Autowired
    private PlaylistVideoRepository playlistVideoRepository;

    public void created(String playlist, String videoId) {

        PlaylistVideoEntity entity = new PlaylistVideoEntity();
        entity.setVideoId(videoId);
        entity.setPlaylistId(playlist);

        playlistVideoRepository.save(entity);
    }
}
