package com.tlc.cansingtone.service.chart;

import com.tlc.cansingtone.domain.chart.KaraokeTopChart;
import com.tlc.cansingtone.dto.song.ResSongDto;
import com.tlc.cansingtone.repository.chart.KaraokeTopChartRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

@Service
public class KaraokeTopChartService {
    private final KaraokeTopChartRepository karaokeTopChartRepository;

    public KaraokeTopChartService(KaraokeTopChartRepository karaokeTopChartRepository) {
        this.karaokeTopChartRepository = karaokeTopChartRepository;
    }

    public List<KaraokeTopChart> getKaraokeTopChart() {
        List<KaraokeTopChart> karaokeTopChart = karaokeTopChartRepository.findAllByOrderByRankingAsc();
        if (karaokeTopChart == null || karaokeTopChart.isEmpty()) {
            throw new BusinessException(ErrorCode.EMPTY_DATA);
        }
        return karaokeTopChart;
    }

}
