package com.tlc.cansingtone.service;

import com.tlc.cansingtone.domain.Timbre;
import com.tlc.cansingtone.dto.timbre.ResTimbreDto;
import com.tlc.cansingtone.repository.TimbreRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class TimbreService {
    private final TimbreRepository timbreRepository;

    public TimbreService(TimbreRepository timbreRepository) {
        this.timbreRepository = timbreRepository;
    }

    public ResTimbreDto createTimbre(String timbreUrl, String userId) {
        Timbre newTimbre = new Timbre();
        newTimbre.setTimbreUrl(timbreUrl);
        newTimbre.setUserId(userId);

        // 유저의 현재 음색 개수를 조회
        int timbreCount = timbreRepository.getTimbreCountForUser(userId);
        String timbreName = "음색" + (timbreCount + 1);
        newTimbre.setTimbreName(timbreName);

        Timbre savedTimbre = timbreRepository.save(newTimbre);
        return new ResTimbreDto(savedTimbre);
    }

    public ResTimbreDto getTimbreByTimbreId(Long timbreId) {
        Timbre timbre = timbreRepository.findByTimbreId(timbreId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        return new ResTimbreDto(timbre);
    }

    public List<Timbre> getTimbreByUserId(String userId) {
        List<Timbre> timbres = timbreRepository.findByUserId(userId);
        if (timbres.isEmpty()) {
            throw new BusinessException(ErrorCode.EMPTY_DATA);
        }
        return timbres;
    }

    public ResTimbreDto updateTimbreName(Long timbreId, String timbreName) {
        Timbre timbre = timbreRepository.findByTimbreId(timbreId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        timbre.setTimbreName(timbreName);
        Timbre updatedTimbre = timbreRepository.save(timbre);
        return new ResTimbreDto(updatedTimbre);
    }

    public void deleteTimbre(Long timbreId) {
        Timbre timbre = timbreRepository.findByTimbreId(timbreId).orElseThrow(() -> new BusinessException(ErrorCode.EMPTY_DATA));
        timbreRepository.delete(timbre);
    }

}
