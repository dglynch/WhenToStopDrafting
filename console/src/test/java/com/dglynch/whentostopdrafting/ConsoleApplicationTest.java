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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Tag("integration")
class ConsoleApplicationTest {

    @Test
    void mainPrintsSomethingToTheConsoleWhenUsingAllDefaultArguments() throws Exception {
        String text = tapSystemOutNormalized(() -> {
            ConsoleApplication.main(new String[0]);
        });
        assertThat(text, is(not(emptyOrNullString())));
    }

    @Test
    void mainPrintsExpectedCompletionWhenValidArgumentsAreSpecified() throws Exception {
        String text = tapSystemOutNormalized(() -> {
            ConsoleApplication.main(new String[]{"src/test/resources/Player.log", "src/test/resources/"});
        });
        assertThat(text, containsString("You have collected 88 of 256 rares in ZNR.\n"));
    }

    @Test
    void mainPrintsExpectedInventorySummaryWhenValidArgumentsAreSpecified() throws Exception {
        String text = tapSystemOutNormalized(() -> {
            ConsoleApplication.main(new String[]{"src/test/resources/Player.log", "src/test/resources/"});
        });
        assertThat(text, containsString("You have 70 unopened booster packs of ZNR.\n"));
    }

    @Test
    void mainPrintsExpectedMessageWhenSpecifiedPlayerLogFileIsMissingData() throws Exception {
        String text = tapSystemOutNormalized(() -> {
            ConsoleApplication.main(new String[]{"src/test/resources/missingdata.log", "src/test/resources/"});
        });
        assertThat(text, is(equalTo("No collection data found at src/test/resources/missingdata.log\n")));
    }

    @Test
    void mainPrintsExpectedMessageWhenDataInSpecifiedPlayerLogFileIsMalformed() throws Exception {
        String text = tapSystemOutNormalized(() -> {
            ConsoleApplication.main(new String[]{"src/test/resources/malformed.log", "src/test/resources/"});
        });
        assertThat(text, is(equalTo("No collection data found at src/test/resources/malformed.log\n")));
    }

    @Test
    void mainPrintsExpectedMessageWhenInvalidPlayerLogFilePathIsSpecified() throws Exception {
        String text = tapSystemOutNormalized(() -> {
            ConsoleApplication.main(new String[]{"foobarbaz", "src/test/resources/"});
        });
        assertThat(text, is(equalTo("No collection data found at foobarbaz\n")));
    }

    @Test
    void mainPrintsExpectedMessageWhenInvalidDownloadsDataPathPrefixIsSpecified() throws Exception {
        String text = tapSystemOutNormalized(() -> {
            ConsoleApplication.main(new String[]{"src/test/resources/Player.log", "src/test/resources/foobarbaz/"});
        });
        assertThat(text, is(equalTo("Failed to find required data files at src/test/resources/foobarbaz/\n")));
    }
}
