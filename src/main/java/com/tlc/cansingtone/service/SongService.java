package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Song;
import com.tlc.cansingtone.dto.song.ResSongDto;
import com.tlc.cansingtone.repository.SongRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public ResSongDto getSongbySongId(Long songId) {
        Song song = songRepository.findBySongId(songId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        ResSongDto response = new ResSongDto(song);
        return response;
    }

//    public List<Song> getSongsbyTitleOrArtist(String keyword) {
//        List<Song> songs = songRepository.findByArtistContainingIgnoreCaseOrSongTitleContainingIgnoreCase(keyword, keyword);
//        return songs;
//    }

    public List<Song> getSongsByKeywordAndGenreAndVocalRange(List<Integer> genres, Integer highestNote, Integer lowestNote, String keyword) {

        final int maxNote = (highestNote == -1) ? Integer.MAX_VALUE : highestNote;
        final int minNote = (lowestNote == -1) ? Integer.MIN_VALUE : lowestNote;

        Specification<Song> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Genre filtering
            if (genres != null && !genres.isEmpty()) {
                predicates.add(root.get("genre").in(genres));
            }

            // Vocal range filtering
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("highestNote"), maxNote));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("lowestNote"), minNote));

            // 키워드 필터링
            if (keyword != null && !keyword.isEmpty()) {
                String[] keywords = keyword.split(" ");
                List<Predicate> keywordPredicates = new ArrayList<>();
                for (String key : keywords) {
                    String lowerKeyword = "%" + key.toLowerCase() + "%";
                    keywordPredicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("artist")), lowerKeyword),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("songTitle")), lowerKeyword)
                    ));
                }
                predicates.add(criteriaBuilder.and(keywordPredicates.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return songRepository.findAll(spec);
    }
}
