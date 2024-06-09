package com.tlc.cansingtone.service.chart;

import com.tlc.cansingtone.domain.chart.PersonalizedChart;
import com.tlc.cansingtone.repository.chart.PersonalizedChartRepository;
import com.tlc.cansingtone.exception.BusinessException;
import com.tlc.cansingtone.exception.ErrorCode;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

@Service
public class PersonalizedChartService {
    private final PersonalizedChartRepository personalizedChartRepository;

    public PersonalizedChartService(PersonalizedChartRepository personalizedChartRepository) {
        this.personalizedChartRepository = personalizedChartRepository;
    }

    public List<PersonalizedChart> getPersonalizedChart(int age, int gender) {
        return personalizedChartRepository.findByAgeAndGender(age, gender);
    }
}
