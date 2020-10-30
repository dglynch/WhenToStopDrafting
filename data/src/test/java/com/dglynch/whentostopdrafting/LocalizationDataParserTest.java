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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LocalizationDataParserTest {

    private static Map<Integer, String> localization;

    @BeforeAll
    static void beforeAll() {
        LocalizationDataParser localizationDataParser =
                new LocalizationDataParser("src/test/resources/data_loc_1a2b3c4d.mtga");
        localization = localizationDataParser.readLocalization();
    }

    @Test
    void readLocalizationHasExpectedSize() {
        assertThat(localization, is(aMapWithSize(16_993)));
    }

    @Test
    void readLocalizationContainsSomeExpectedEntries() {
        assertThat(localization, hasEntry(336746, "Steady Aim"));
        assertThat(localization, hasEntry(336952, "God-Pharaoh's Statue"));
        assertThat(localization, hasEntry(437118, "Concerted Defense"));
        assertThat(localization, hasEntry(437005, "Kor Blademaster"));
    }

    @Test
    void readLocalizationReturnsEmptyMapWhenInputDataIsMissing() {
        LocalizationDataParser localizationDataParser = new LocalizationDataParser("src/test/resources/filedoesnotexist.mtga");
        Map<Integer, String> missingLocalization = localizationDataParser.readLocalization();
        assertThat(missingLocalization, is(anEmptyMap()));
    }

    @Test
    void readLocalizationReturnsEmptyMapWhenInputDataIsMalformed() {
        LocalizationDataParser localizationDataParser = new LocalizationDataParser("src/test/resources/malformedcollection.log");
        Map<Integer, String> malformedLocalization = localizationDataParser.readLocalization();
        assertThat(malformedLocalization, is(anEmptyMap()));
    }
}
