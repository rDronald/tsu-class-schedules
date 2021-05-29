package com.rdronald.front.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdronald.front.model.Audience;
import com.rdronald.front.model.Group;
import com.rdronald.front.model.Institute;
import com.rdronald.front.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.rdronald.front.url.UrlTemplates.URL_GET_SCHEDULE_FOR_GROUP;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Lesson> getScheduleForGroup(Audience audience, Group group, Institute institute, LocalDate date) {
        String url = URL_GET_SCHEDULE_FOR_GROUP.replace("{audience}", audience.toString())
                .replace("{institute}", institute.toString())
                .replace("{group}", group.toString())
                .replace("{date}", DateTimeFormatter.ofPattern("dd.MM.yyyy").format(date));

        JsonNode objects = restTemplate.getForObject(url, JsonNode.class);
        return mapper.convertValue(objects,
                new TypeReference<List<Lesson>>() {
                }
        );
    }
}
