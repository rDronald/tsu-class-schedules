package com.rdronald.db.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdronald.db.model.Group;
import com.rdronald.db.repository.GroupRepository;
import com.rdronald.db.utility.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.rdronald.db.url.UrlTemplates.PATH_GROUPS;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public void add(Group group) {
        group.setId(UUID.randomUUID().toString());
        groupRepository.save(group);
    }

    public void addDefaults() {
        ObjectMapper mapper = new ObjectMapper();
        Iterable<Group> groups = mapper.convertValue(JsonReader.getStandardObjects(PATH_GROUPS),
                new TypeReference<Iterable<Group>>() {
                }
        );

        groups.forEach(this::add);
    }
}
