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

class CollectionParserTest {

    private static Map<String, Integer> collection;

    @BeforeAll
    static void beforeAll() {
        CollectionParser collectionParser = new CollectionParser("src/test/resources/collection.log");
        collection = collectionParser.readCollection();
    }

    @Test
    void readCollectionHasExpectedSize() {
        assertThat(collection, is(aMapWithSize(1824)));
    }

    @Test
    void readCollectionContainsSomeExpectedEntries() {
        assertThat(collection, hasEntry("69628", 1));
        assertThat(collection, hasEntry("69689", 2));
        assertThat(collection, hasEntry("73241", 3));
        assertThat(collection, hasEntry("73205", 4));
    }

    @Test
    void readCollectionDoesNotContainAnyEntryWithCountTooHigh() {
        assertThat(collection, not(hasValue(greaterThan(4))));
    }

    @Test
    void readCollectionDoesNotContainAnyEntryWithCountTooLow() {
        assertThat(collection, not(hasValue(lessThan(1))));
    }

    @Test
    void readCollectionOnlyTakesDataFromMostRecentPlayerInventory() {
        assertThat(collection, hasEntry("69855", 3));
        assertThat(collection, hasEntry("72017", 4));
    }

    @Test
    void readCollectionReturnsEmptyMapWhenInputDataIsMissing() {
        CollectionParser collectionParser = new CollectionParser("src/test/resources/missingdata.log");
        Map<String, Integer> missingCollection = collectionParser.readCollection();
        assertThat(missingCollection, is(anEmptyMap()));
    }

    @Test
    void readCollectionReturnsEmptyMapWhenInputDataIsMalformed() {
        CollectionParser collectionParser = new CollectionParser("src/test/resources/malformedcollection.log");
        Map<String, Integer> malformedCollection = collectionParser.readCollection();
        assertThat(malformedCollection, is(anEmptyMap()));
    }
}
