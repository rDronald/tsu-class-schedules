package com.rdronald.db.controller;

import com.rdronald.db.model.Group;
import com.rdronald.db.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rdronald.db.url.UrlTemplates.LOCAL_URL_GROUP_MANAGEMENT;
import static com.rdronald.db.url.UrlTemplates.LOCAL_URL_POST;

@RestController
@RequestMapping(LOCAL_URL_GROUP_MANAGEMENT)
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;

    @GetMapping(LOCAL_URL_POST)
    public void postGroup() {
        Group group = new Group();
        group.setName("ПМим-2001а");

        groupRepository.save(group);
    }
}
