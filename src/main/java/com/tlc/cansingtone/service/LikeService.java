package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Like;
import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.domain.SongInPlaylist;
import com.tlc.cansingtone.dto.like.ReqLikeDto;
import com.tlc.cansingtone.dto.like.ResLikeDto;
import com.tlc.cansingtone.dto.like.ResSongInLikeListDto;
import com.tlc.cansingtone.repository.LikeRepository;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final SongRepository songRepository;

    public LikeService(LikeRepository likeRepository, SongRepository songRepository) {
        this.likeRepository = likeRepository;
        this.songRepository = songRepository;
    }

    public Long createLike(String userId, Long songId) {
        Like like = new Like();
        like.setUserId(userId);
        like.setSongId(songId);

        Like savedLike = likeRepository.save(like);
        return savedLike.getLikeId();
    }

    public List<ResSongInLikeListDto> getLikedSongsByUserId(String userId) {
        List<Like> likes = likeRepository.findByUserId(userId);
        List<ResSongInLikeListDto> likesWithDetails = new ArrayList<>();

        for (Like like : likes) {
            Song song = songRepository.findById(like.getSongId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));

            likesWithDetails.add(new ResSongInLikeListDto(like.getLikeId(), like.getUserId(), song));
        }
        return likesWithDetails;
    }

}
