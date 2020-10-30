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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DownloadsDataFileFinderTest {

    private static final String SRC_TEST_RESOURCES_DIRECTORY_PREFIX =
            "src" + File.separator + "test" + File.separator + "resources" + File.separator;

    private DownloadsDataFileFinder downloadsDataFileFinder;

    @BeforeEach
    void setUp() {
        downloadsDataFileFinder = new DownloadsDataFileFinder("src/test/resources/");
    }

    @Test
    void findFilePathReturnsCorrectPathForLocalizationFile() throws IOException {
        String actual = downloadsDataFileFinder.findFilePath("loc");
        assertThat(actual, is(equalTo(SRC_TEST_RESOURCES_DIRECTORY_PREFIX + "data_loc_1a2b3c4d.mtga")));
    }

    @Test
    void findFilePathReturnsCorrectPathForCardsFile() throws IOException {
        String actual = downloadsDataFileFinder.findFilePath("cards");
        assertThat(actual, is(equalTo(SRC_TEST_RESOURCES_DIRECTORY_PREFIX + "data_cards_4a3b2c1d.mtga")));
    }

    @Test
    void findFilePathThrowsExceptionForUnrecognizedFileType() {
        assertThrows(FileNotFoundException.class, () -> {
            downloadsDataFileFinder.findFilePath("foobar");
        });
    }
}
