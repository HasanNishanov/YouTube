package com.company.service;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.tag.TagUpdateDTO;
import com.company.dto.tag.TagDTO;
import com.company.entity.TagEntity;
import com.company.exp.ItemNotFoundException;
import com.company.repository.TagRepository;
import com.company.repository.VideoTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private VideoTagRepository videoTagRepository;

    public TagEntity created(String name){

        TagEntity tagEntity = new TagEntity();
        tagEntity.setName(name.toUpperCase());

        tagRepository.save(tagEntity);

        return tagEntity;
    }

    public TagEntity createdIfNotExist(String tagName) {

        Optional<TagEntity> optional = tagRepository.findByName(tagName.toUpperCase());
        return optional.orElseGet(() -> created(tagName));

    }

    public TagDTO getTagDTO(TagEntity entity) {

        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(entity.getId());
        tagDTO.setCreatedDate(entity.getCreatedDate());
        tagDTO.setVisible(entity.getVisible());
        tagDTO.setName(entity.getName());
        tagDTO.setStatus(entity.getStatus());

        return tagDTO;
    }

    public List<String> getTagListByVideoId(String videoId) {

        return videoTagRepository.getAllTagNameByVideoId(videoId);
    }

    public ResponseInfoDTO update(Integer id, TagUpdateDTO dto) {

        TagEntity tag = get(id);
        tag.setName(dto.getName());
        tag.setStatus(dto.getStatus());

        tagRepository.save(tag);

        return new ResponseInfoDTO(1,"success");
    }

    private TagEntity get(Integer id) {

        return tagRepository.findById(id).orElseThrow(() -> {
            return new ItemNotFoundException("tag not fount");
        });
    }
    public ResponseInfoDTO changeVisible(Integer id) {

        TagEntity tag = get(id);

        tag.setVisible(!tag.getVisible());

        tagRepository.save(tag);

        return new ResponseInfoDTO(1,"success");
    }

}
