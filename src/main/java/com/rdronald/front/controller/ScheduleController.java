package com.rdronald.front.controller;

import com.rdronald.front.exception.BadRequestException;
import com.rdronald.front.model.Audience;
import com.rdronald.front.model.Group;
import com.rdronald.front.model.Institute;
import com.rdronald.front.model.Lesson;
import com.rdronald.front.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

import static com.rdronald.front.url.UrlTemplates.LOCAL_URL_GET_SCHEDULE_FOR_GROUP;

@Controller
public class ScheduleController {
    @Autowired
    private LessonService lessonService;

    @GetMapping(LOCAL_URL_GET_SCHEDULE_FOR_GROUP)
    public String getScheduleForGroup(Model model,
                                      @PathVariable(name = "audience") String audienceStr,
                                      @PathVariable(name = "group") String groupStr,
                                      @PathVariable(name = "institute") String instituteStr) {
        Audience audience;
        Group group;
        Institute institute;
        try {
            audience = Audience.valueOf(audienceStr);
            group = Group.valueOf(groupStr.toUpperCase());
            institute = Institute.valueOf(instituteStr.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException();
        }

        List<Lesson> lessons = lessonService.getScheduleForGroup(audience, group, institute, LocalDate.now());
        model.addAttribute("lessons", lessons);

        return "schedule";
    }
}
