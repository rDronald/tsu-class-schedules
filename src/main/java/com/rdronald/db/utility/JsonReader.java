package com.rdronald.db.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class JsonReader {
    private JsonReader() {
    }

    public static JsonNode getStandardObjects(String path) {
        ObjectMapper mapper = new ObjectMapper();
        File from = new File(path);

        try {
            return mapper.readTree(from);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
