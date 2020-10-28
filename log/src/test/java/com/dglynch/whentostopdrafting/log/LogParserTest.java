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
