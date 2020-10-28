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

package com.dglynch.whentostopdrafting.log;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class LogParserTest {

    @Test
    void readCollection() {
        LogParser logParser = new LogParser("src/test/resources/collection.log");

        Map<String, Integer> actual = logParser.readCollection();

        assertThat(actual, is(aMapWithSize(1824)));

        assertThat(actual, hasEntry("69628", 1));
        assertThat(actual, hasEntry("69689", 2));
        assertThat(actual, hasEntry("73241", 3));
        assertThat(actual, hasEntry("73205", 4));

        assertThat(actual, not(hasValue(greaterThan(4))));
        assertThat(actual, not(hasValue(lessThan(1))));
    }
}
