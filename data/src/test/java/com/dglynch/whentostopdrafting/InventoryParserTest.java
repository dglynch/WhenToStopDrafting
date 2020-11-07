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

class InventoryParserTest {

    private static Map<String, Integer> inventory;

    @BeforeAll
    static void beforeAll() {
        InventoryParser inventoryParser = new InventoryParser("src/test/resources/inventory.log");
        inventory = inventoryParser.readInventory();
    }

    @Test
    void readInventoryHasExpectedSize() {
        assertThat(inventory, is(aMapWithSize(3)));
    }

    @Test
    void readInventoryContainsSomeExpectedEntries() {
        assertThat(inventory, hasEntry("ZNR", 70));
        assertThat(inventory, hasEntry("ELD", 1));
        assertThat(inventory, hasEntry("IKO", 2));
    }

    @Test
    void readInventoryDoesNotContainAnyEntryWithCountTooLow() {
        assertThat(inventory, not(hasValue(lessThan(1))));
    }

    @Test
    void readInventoryOnlyTakesDataFromMostRecentPlayerInventory() {
        assertThat(inventory, not(hasEntry("IKO", 1)));
    }

    @Test
    void readInventoryReturnsEmptyMapWhenInputDataIsMissing() {
        InventoryParser inventoryParser = new InventoryParser("src/test/resources/missingdata.log");
        Map<String, Integer> missingInventory = inventoryParser.readInventory();
        assertThat(missingInventory, is(anEmptyMap()));
    }

    @Test
    void readInventoryReturnsEmptyMapWhenInputDataIsMalformed() {
        InventoryParser inventoryParser = new InventoryParser("src/test/resources/malformedinventory.log");
        Map<String, Integer> malformedInventory = inventoryParser.readInventory();
        assertThat(malformedInventory, is(anEmptyMap()));
    }
}
