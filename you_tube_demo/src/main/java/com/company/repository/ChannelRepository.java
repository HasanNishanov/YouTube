package com.company.repository;

import com.company.dto.channel.ChannelShortDTO;
import com.company.dto.channel.ChannelShortInfoDTO;
import com.company.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChannelRepository extends JpaRepository<ChannelEntity, String> {

    @Query(value = "select c.name,c.created_date, c.uuid, c.status,c.visible,c.profile_id as pId ,c.photo_id as photo " +
            "from channel c " +
            "order by c.created_date " +
            "limit :limit " +
            "offset :offset", nativeQuery = true)
    List<ChannelShortDTO> pagination(@Param("limit") Integer limit, @Param("offset") Integer offset);

    List<ChannelEntity> findAllByVisibleAndProfileId(Boolean visible, Integer profileId);
}
