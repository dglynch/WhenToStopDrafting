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

public class CardsDataParserTest {

    private static Map<Integer, String> localization;

    private static Map<Integer, Card> cards;

    @BeforeAll
    static void beforeAll() {
        localization = new LocalizationDataParser("src/test/resources/data_loc_1a2b3c4d.mtga").readLocalization();

        CardsDataParser cardsDataParser = new CardsDataParser("src/test/resources/data_cards_4a3b2c1d.mtga", localization);
        cards = cardsDataParser.readCards();
    }

    @Test
    void readCardsHasExpectedSize() {
        assertThat(cards, is(aMapWithSize(5527)));
    }

    @Test
    void readCardsContainsSomeExpectedEntries() {
        assertThat(cards, hasEntry(69628, new Card("Steady Aim", Rarity.COMMON)));
        assertThat(cards, hasEntry(69689, new Card("God-Pharaoh's Statue", Rarity.UNCOMMON)));
        assertThat(cards, hasEntry(73241, new Card("Concerted Defense", Rarity.UNCOMMON)));
        assertThat(cards, hasEntry(73205, new Card("Kor Blademaster", Rarity.UNCOMMON)));
        assertThat(cards, hasEntry(74676, new Card("Lotus Cobra", Rarity.RARE)));
        assertThat(cards, hasEntry(70285, new Card("Robber of the Rich", Rarity.MYTHIC_RARE)));
    }

    @Test
    void readCardsReturnsEmptyMapWhenInputDataIsMissing() {
        CardsDataParser cardsDataParser = new CardsDataParser("src/test/resources/filedoesnotexist.mtga", localization);
        Map<Integer, Card> missingCards = cardsDataParser.readCards();
        assertThat(missingCards, is(anEmptyMap()));
    }

    @Test
    void readCardsReturnsEmptyMapWhenInputDataIsMalformed() {
        CardsDataParser cardsDataParser = new CardsDataParser("src/test/resources/malformedcollection.log", localization);
        Map<Integer, Card> malformedCards = cardsDataParser.readCards();
        assertThat(malformedCards, is(anEmptyMap()));
    }
}
