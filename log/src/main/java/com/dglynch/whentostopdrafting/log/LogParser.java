package com.dglynch.whentostopdrafting.log;

import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class LogParser {
    private static final String COLLECTION_PREFIX = "[UnityCrossThreadLogger]<== PlayerInventory.GetPlayerCardsV3 ";

    private final String logFilePath;

    public LogParser(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public Map<String, Integer> readCollection() {
        try {
            String collectionLine = Files.lines(Path.of(logFilePath))
                    .filter(s -> s.startsWith(COLLECTION_PREFIX))
                    .reduce((first, second) -> second).orElseThrow(); // finds the last element of the stream

            String jsonString = collectionLine.substring(COLLECTION_PREFIX.length());

            return JsonParser.parseString(jsonString).getAsJsonObject()
                    .get("payload").getAsJsonObject()
                    .entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().getAsInt()));
        } catch (IOException | NoSuchElementException e) {
            return Map.of();
        }
    }
}
