package com.tlc.cansingtone.repository.chart;

import com.tlc.cansingtone.domain.chart.PersonalizedChart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalizedChartRepository extends JpaRepository<PersonalizedChart, Long> {
    List<PersonalizedChart> findByAgeAndGender(int age, int gender);

}

