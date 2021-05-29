package com.rdronald.front.service;

import com.rdronald.front.model.Audience;
import com.rdronald.front.model.Group;
import com.rdronald.front.model.Institute;
import com.rdronald.front.model.Lesson;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {
    List<Lesson> getScheduleForGroup(Audience audience, Group group, Institute institute, LocalDate date);
}
