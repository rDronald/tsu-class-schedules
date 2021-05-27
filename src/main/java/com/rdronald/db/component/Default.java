package com.rdronald.db.component;

import com.rdronald.db.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Default {
    @Autowired
    private GroupService groupService;

    @EventListener(ApplicationReadyEvent.class)
    public void addDefaults() {
        groupService.addDefaults();
        getDefaultLinks();

        System.out.println("Defaults added!");
    }

    public static void getDefaultLinks() {
    }
}
