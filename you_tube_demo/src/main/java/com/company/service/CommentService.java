package com.company.service;

import com.company.dto.comment.CommentCreateDTO;
import com.company.dto.comment.CommentDTO;
import com.company.dto.comment.CommentUpdateDTO;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ProfileService profileService;

    public CommentDTO create(CommentCreateDTO dto) {

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(dto.getContent());

        ProfileEntity profileEntity = profileService.getProfile();
      //  ArticleEntity articleEntity = articleService.get(dto.getArticleId());

        if (dto.getCommentId() != null) {
            CommentEntity commentEntity1 = get(dto.getCommentId());
            commentEntity.setComment(commentEntity1);
        }

        commentEntity.setProfile(profileEntity);
     //   commentEntity.setVideo(articleEntity);

        commentRepository.save(commentEntity);
        return getDTO(commentEntity);

    }

    public CommentDTO getDTO(CommentEntity commentEntity) {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setContent(commentEntity.getContent());
        commentDTO.setCreatedDate(commentEntity.getCreatedDate());
        commentDTO.setUpdateDate(commentEntity.getUpdateDate());
        commentDTO.setVisible(commentEntity.getVisible());

    //    commentDTO.setArticle(articleService.getDTO(commentEntity.getArticleEntity()));
        commentDTO.setProfile(profileService.getProfileDTOById(commentEntity.getProfile().getId()));

        if (commentEntity.getComment() != null) {
            commentDTO.setComment(getDTO(commentEntity.getComment()));
        }

        return commentDTO;
    }

    public CommentEntity get(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> {
            throw new ItemNotFoundException("Comment Not found");
        });
    }

    public CommentDTO update(CommentUpdateDTO dto, Integer commentId) {

        CommentEntity commentEntity = get(commentId);

        ProfileEntity entity = profileService.getProfile();
        if (entity.getRole().equals(ProfileRole.ROLE_USER) &&
                !commentEntity.getProfile().getId().equals(entity.getId())){
            throw new ItemNotFoundException("NO ACCESS");
        }

      //  commentEntity.setCommentEntity(get(dto.getCommentId()));
        commentEntity.setUpdateDate(LocalDateTime.now());
        commentEntity.setContent(dto.getContent());

        commentRepository.save(commentEntity);

        return getDTO(commentEntity);
    }

    public CommentDTO changeVisible( Integer commentId) {

        CommentEntity commentEntity = get(commentId);

        ProfileEntity entity = profileService.getProfile();
        if (entity.getRole().equals(ProfileRole.ROLE_USER) &&
                !commentEntity.getProfile().getId().equals(entity.getId())){
            throw new ItemNotFoundException("NO ACCESS");
        }

        commentEntity.setVisible(!commentEntity.getVisible());

        return getDTO(commentEntity);
    }
}
