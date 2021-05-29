package com.rdronald.db.service;

import com.rdronald.db.model.StudyWeek;
import com.rdronald.db.repository.StudyWeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StudyWeekService {
    @Autowired
    private StudyWeekRepository studyWeekRepository;

    public void save(StudyWeek studyWeek) {
        studyWeekRepository.save(studyWeek);
    }

    public Iterable<StudyWeek> findAll() {
        return studyWeekRepository.findAll();
    }

    public Optional<StudyWeek> strToObj(String studyWeek) {
        Pattern pattern = Pattern.compile("\\s+\\d+.+неделя",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(studyWeek);

        int number;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group().replaceAll("[^\\d]", ""));
        } else {
            return Optional.empty();
        }

        pattern = Pattern.compile("\\(.+\\)",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        matcher = pattern.matcher(studyWeek);

        if (matcher.find()) {
            String[] datesStr = matcher.group().replaceAll("[^\\d.-]", "").split("-");
            List<LocalDate> dates = Arrays.stream(datesStr).map(x -> {
                String[] arr = x.split("\\.");
                return LocalDate.of(Integer.parseInt(arr[2]), Integer.parseInt(arr[1]),
                        Integer.parseInt(arr[0]));
            }).collect(Collectors.toList());

            return Optional.of(new StudyWeek(number, dates.get(0), dates.get(dates.size() - 1)));
        } else {
            return Optional.empty();
        }
    }
}
