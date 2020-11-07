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
import java.util.stream.StreamSupport;

public class InventoryParser {

    private static final String INVENTORY_PREFIX = "[UnityCrossThreadLogger]<== PlayerInventory.GetPlayerInventory ";

    private static final Map<Integer, String> SET_NAMES = Map.of(
            100015, "ELD",
            100017, "IKO",
            100020, "ZNR"
    );

    private final String logFilePath;

    public InventoryParser(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public Map<String, Integer> readInventory() {
        try {
            String collectionLine = Files.lines(Path.of(logFilePath))
                    .filter(s -> s.startsWith(INVENTORY_PREFIX))
                    .reduce((first, second) -> second).orElseThrow(); // finds the last element of the stream

            String jsonString = collectionLine.substring(INVENTORY_PREFIX.length());

            return StreamSupport.stream(JsonParser.parseString(jsonString).getAsJsonObject()
                    .get("payload").getAsJsonObject()
                    .get("boosters").getAsJsonArray().spliterator(), false)
                    .collect(Collectors.toMap(
                            element -> SET_NAMES.get(element.getAsJsonObject().get("collationId").getAsInt()),
                            element -> element.getAsJsonObject().get("count").getAsInt()));
        } catch (IOException | NoSuchElementException | JsonParseException e) {
            return Map.of();
        }
    }
}
