package com.company.controller;

import com.company.dto.video.VideoLikeCreateDTO;
import com.company.service.VideoLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "Video Like Controller")
@RestController
@RequestMapping("/video_like")
public class VideoLikeController {

    @Autowired
    private VideoLikeService videoLikeService;

    //     16. Article LikeCreate (ANY)
//        (article_id)
    @ApiOperation(value = "Video Like", notes = "Video like by users")
    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody VideoLikeCreateDTO dto) {

        videoLikeService.videoLike(dto.getVideoId());

        return ResponseEntity.ok("success");
    }

    //    17. Article DisLikeCreate (ANY)
//            (article_id)
    @ApiOperation(value = "Article Dis Like", notes = "Article dis like by users")
    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(@RequestBody VideoLikeCreateDTO dto) {

        videoLikeService.videoDisLike(dto.getVideoId());
        return ResponseEntity.ok().build();

    }

    //      18. Article Like Remove (ANY)
//        (article_id)
    @ApiOperation(value = "Article Like Remove", notes = "Article like remove by users")
    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody VideoLikeCreateDTO dto) {

        videoLikeService.removeLike(dto.getVideoId());
        return ResponseEntity.ok().build();

    }
}
