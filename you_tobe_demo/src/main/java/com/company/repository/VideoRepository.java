package com.company.repository;

import com.company.entity.VideoEntity;
import com.company.mapper.VideoFullInfo;
import com.company.mapper.VideoPlaylistInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, String> {

    @Query(value = " select pv.uuid as playListVideoId, pv.video_id as videoId, v.name as videoName " +
            " from playlist_video as pv " +
            " inner join video as v on v.uuid = pv.video_id " +
            " Where pv.playlist_id =:playlistId " +
            " order by  order_number asc limit 2", nativeQuery = true)
    public List<VideoPlaylistInfo> getVideoFoPlayList(@Param("playlistId") String playlistId);

    /*   VideFullInfo
  (id,key,title,description,   published_date, view_count,shared_count,
     preview_attach(id,url),attach(id,url,duration),
     category(id,name), channel(id,name,photo(url))
      Like(like_count,dislike_count), isUserLiked,IsUserDisliked),duration)

     , tagList(id,name),
    */

    @Query(value = " select v.id as videoId, v.title as videoId, v.description as description, " +
            "  v.published_date as publishedDate, v.view_count as viewCount, v.shared_count as sharedCount " +
            " v.review_id as previewId " +
            " a.uuid as attachId, a.duration as duration " +
            " c.id as categoryId, c.name as categoryName " +
            " ch.id as channelId, ch.name as channelName, ch.photo_id as channelPhotoId " +
            " (select count(*) from video_like  where video_id =:videoId and status = 'LIKE') as videoLikeCount, " +
            " (select count(*) from video_like  where video_id =:videoId and status = 'DISLIKE' ) as videoDislikeCount " +
            " (select status from video_like  where video_id =:videoId and profile_id =:profileId ) as isUserLike " +
            "from video as v " +
            " inner join attach as a on a.uuid = v.attach_id " +
            " inner join category as c on c.id = v.category_id " +
            " inner join channel as ch on ch.id = v.channel_id " +
            "", nativeQuery = true)
    public VideoFullInfo getVideFullIntoById(@Param("videoId") String videoId, @Param("profileId") Integer profileId);


}
