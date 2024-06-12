package com.tlc.cansingtone.repository.chart;

import com.tlc.cansingtone.domain.chart.KaraokeTopChart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KaraokeTopChartRepository extends JpaRepository<KaraokeTopChart, Long> {
    List<KaraokeTopChart> findAllByOrderByRankingAsc();

    Optional<KaraokeTopChart> findBySongId(Long songId);
}
