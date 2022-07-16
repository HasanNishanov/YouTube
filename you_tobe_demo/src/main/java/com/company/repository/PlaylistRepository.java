package com.company.repository;

import com.company.entity.ChannelEntity;
import com.company.entity.PlaylistEntity;
import com.company.mapper.CustomPlaylistRepository;
import com.company.mapper.PlaylistShortInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, String> {

    @Query(value = "select c.* " +
            "from playlist c " +
            "order by c.created_date " +
            "limit :limit " +
            "offset :offset", nativeQuery = true)
    List<PlaylistEntity> pagination(@Param("limit") Integer limit, @Param("offset") Integer offset);

//    id, name,created_date,channel(id,name),video_count,video_list[{id,name,key,duration}] (first 2)

    @Query(value = "SELECT p.uuid as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
            "      c.uuid as channleId, c.name as channelName, " +
            " (select count(*) from playlist_video  as pv where pv.playlist_id = p.uuid ) as countVideo " +
            "     from  playlist as p " +
            " inner JOIN channel as c on p.channel_id = c.uuid " +
            "     where c.profile_id =:profileId " +
            "     and c.visible = true and p.visible = true ", nativeQuery = true)
    List<PlaylistShortInfo> getProfilePlayLists(@Param("profileId") Integer profileId);


    @Query(value = "SELECT p.uuid as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
            "      c.uuid as channleId, c.name as channelName, " +
            " (select count(*) from playlist_video  as pv where pv.playlist_id = p.uuid ) as countVideo " +
            "     from  playlist as p " +
            " inner JOIN channel as c on p.channel_id = c.uuid " +
            "     where c.profile_id =1 " +
            "     and c.visible = true and p.visible = true ", nativeQuery = true)
    List<PlaylistShortInfo> getChannelPlayLists(@Param("profileId") String profileId);


    @Query("select pe.uuid as playlistId,pe.name as playlistName, " +
            " pe.updatedDate as playlistUpdatedDate," +
            " count(pve.uuid) as videoCount, " +
            " sum(pve.video.viewCount) as totalViewCount " +
            " from PlaylistEntity pe " +
            " inner join PlaylistVideoEntity pve" +
            " on pe.uuid=pve.playlistId " +
            " where pe.uuid = :id" +
            " group by pe.uuid,pe.name,pe.updatedDate")
    Optional<CustomPlaylistRepository> getPlaylistById(@Param("id") String id);


    //     id,name,video_count,last_update_date,

    @Query(value = "SELECT p.uuid as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
            " (select count(*) from playlist_video  as pv where pv.playlist_id = p.uuid ) as countVideo " +
            "     from  playlist as p " +
            "     Where p.uuid = :playListId " +
            "     and p.visible = true ", nativeQuery = true)
    PlaylistShortInfo getPlaylistShortInfo(@Param("playListId") String playListId);

    //     total_view_count (shu play listdagi videolarni ko'rilganlar soni),
    @Query(value = " Select cast(count(*) as int) as totalCount" +
            " from profile_watched_video as  pwv " +
            " inner join playlist_video as pv on pv.video_id = pwv.video_id " +
            " Where pv.playlist_id =:playlistId ", nativeQuery = true)
    Integer getTotalWatchedVideoCount(@Param("playListId") String playListId);


    @Query(value = "SELECT p.uuid as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
            "   (select count(*) from playlist_video  as pv where pv.playlist_id = p.uuid ) as countVideo, " +
            "   (select cast(count(*) as int) " +
            "       from profile_watched_video as  pwv " +
            "       inner join playlist_video as pv on pv.video_id = pwv.video_id " +
            "       where pv.playlist_id =:playlistId ) as totalWatchedCount" +
            " from  playlist as p " +
            " Where p.uuid = :playListId " +
            " and p.visible = true ", nativeQuery = true)
    PlaylistShortInfo getPlaylistShortInfoWithTotalWatchedCount(@Param("playListId") String playListId);


}
