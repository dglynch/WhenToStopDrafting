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

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RarityTest {

    @Test
    void fromIntReturnsTokenFrom0() {
        assertThat(Rarity.fromInt(0), is(equalTo(Rarity.TOKEN)));
    }

    @Test
    void fromIntReturnsBasicLandFrom1() {
        assertThat(Rarity.fromInt(1), is(equalTo(Rarity.BASIC_LAND)));
    }

    @Test
    void fromIntReturnsCommonFrom2() {
        assertThat(Rarity.fromInt(2), is(equalTo(Rarity.COMMON)));
    }

    @Test
    void fromIntReturnsUncommonFrom3() {
        assertThat(Rarity.fromInt(3), is(equalTo(Rarity.UNCOMMON)));
    }

    @Test
    void fromIntReturnsRareFrom4() {
        assertThat(Rarity.fromInt(4), is(equalTo(Rarity.RARE)));
    }

    @Test
    void fromIntReturnsMythicRareFrom5() {
        assertThat(Rarity.fromInt(5), is(equalTo(Rarity.MYTHIC_RARE)));
    }

    @Test
    void fromIntThrowsFromNegative1() {
        assertThrows(IllegalArgumentException.class, () -> Rarity.fromInt(-1));
    }

    @Test
    void fromIntThrowsFrom6() {
        assertThrows(IllegalArgumentException.class, () -> Rarity.fromInt(6));
    }
}
