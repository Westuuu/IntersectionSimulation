package com.intersectionsimulation.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intersectionsimulation.model.command.Command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ConfigReader {
    private final ObjectMapper mapper;
    private final Resource resource;

    public ConfigReader(ObjectMapper objectMapper, @Value("classpath:commands.json") Resource resource) {
        this.mapper = objectMapper;
        this.resource = resource;
    }

    public List<Command> readCommands() throws IOException {
        return readCommands(this.resource);
    }

    public List<Command> readCommands(Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            JsonNode root = mapper.readTree(inputStream);
            JsonNode commands = root.get("commands");

            return mapper.convertValue(commands, new TypeReference<List<Command>>() {});
        }
    }
}