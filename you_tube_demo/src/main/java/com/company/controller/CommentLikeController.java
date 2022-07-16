package com.company.controller;

import com.company.dto.comment.CommentLikeCreateDTO;
import com.company.service.CommentLikeService;
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
@Api(value = "Comment Like Controller")
@RestController
@RequestMapping("/comment_like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @ApiOperation("Comment Like created method")
    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody CommentLikeCreateDTO dto
                                 // HttpServletRequest request
    ){
    //    Integer id = HttpHeaderUtil.getId(request);
        commentLikeService.commentLike(dto.getCommentId());

        log.info("Request for comment like by  commentId: {}", dto.getCommentId());
        return ResponseEntity.ok("success");
    }
    @ApiOperation("Comment Dislike created method")
    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(@RequestBody CommentLikeCreateDTO dto
    //                              HttpServletRequest request
    ){

      //  Integer id = HttpHeaderUtil.getId(request);
        commentLikeService.commentDisLike(dto.getCommentId());
        log.info("Request for comment dislike by commentId: {}", dto.getCommentId());
        return ResponseEntity.ok().build();
    }
    @ApiOperation("Comment Like remove created method")
    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody CommentLikeCreateDTO dto
    //                              HttpServletRequest request
    ){

    //    Integer id = HttpHeaderUtil.getId(request);
        commentLikeService.removeLike(dto.getCommentId());
        log.info("Request for comment like remove by commentId: {}", dto.getCommentId());
        return ResponseEntity.ok().build();
    }
}
