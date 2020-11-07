/*
    Copyright 2020 Dan Lynch

    This file is part of When To Stop Drafting.

    When To Stop Drafting is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option) any
    later version.

    This program is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
    FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
    details.

    You should have received a copy of the GNU General Public License along with
    this program; if not, see <http://www.gnu.org/licenses>.
*/

package com.dglynch.whentostopdrafting;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CollectionParser {
    private static final String COLLECTION_PREFIX = "[UnityCrossThreadLogger]<== PlayerInventory.GetPlayerCardsV3 ";

    private final String logFilePath;

    public CollectionParser(String logFilePath) {
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
        } catch (IOException | NoSuchElementException | JsonParseException e) {
            return Map.of();
        }
    }
}
