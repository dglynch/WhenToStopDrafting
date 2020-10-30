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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadsDataFileFinder {

    private final String dataFilePathPrefix;

    public DownloadsDataFileFinder(String dataFilePathPrefix) {
        this.dataFilePathPrefix = dataFilePathPrefix;
    }

    public String findFilePath(String fileType) throws IOException {
        return Files.list(Path.of(dataFilePathPrefix))
                .filter(path -> {
                    String fileName = path.getFileName().toString();
                    return fileName.startsWith("data_" + fileType + "_") && fileName.endsWith(".mtga");
                })
                .findAny()
                .map(Path::toString)
                .orElseThrow(FileNotFoundException::new);
    }
}
