package com.rdronald.db.controller.rest;

import com.rdronald.db.model.Audience;
import com.rdronald.db.model.Group;
import com.rdronald.db.model.Institute;
import com.rdronald.db.model.Lesson;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.rdronald.db.url.UrlTemplates.LOCAL_URL_GET_SCHEDULE_FOR_GROUP;
import static com.rdronald.db.url.UrlTemplates.LOCAL_URL_SCHEDULE_MANAGEMENT;

@RestController
@RequestMapping(LOCAL_URL_SCHEDULE_MANAGEMENT)
public class ScheduleRestController {
    @GetMapping(LOCAL_URL_GET_SCHEDULE_FOR_GROUP)
    public List<Lesson> getScheduleForGroup(@RequestParam(name = "audience") String audienceStr,
                                            @RequestParam(name = "group") String groupStr,
                                            @RequestParam(name = "institute") String instituteStr) {
        Audience audience;
        Group group;
        Institute institute;
        try {
            audience = Audience.valueOf(audienceStr);
            group = Group.valueOf(groupStr);
            institute = Institute.valueOf(instituteStr);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return null;
        }

        if (audience.equals(Audience.STUDENTS)) {

        }

        return Collections.emptyList();
    }
}
