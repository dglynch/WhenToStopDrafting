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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LocalizationDataParser {

    private final String localizationDataFilePath;

    public LocalizationDataParser(String localizationDataFilePath) {
        this.localizationDataFilePath = localizationDataFilePath;
    }

    public Map<Integer, String> readLocalization() {
        try {
            String jsonString = Files.readString(Path.of(localizationDataFilePath));

            Map<Integer, String> localization = new HashMap<>();
            JsonParser.parseString(jsonString).getAsJsonArray()
                    .get(0).getAsJsonObject()
                    .get("keys").getAsJsonArray()
                    .forEach(jsonElement -> {
                        int id = jsonElement.getAsJsonObject().get("id").getAsInt();
                        String text = jsonElement.getAsJsonObject().get("text").getAsString();
                        localization.put(id, text);
                    });

            return Collections.unmodifiableMap(localization);
        } catch (IOException | NoSuchElementException | JsonParseException e) {
            return Map.of();
        }
    }
}
