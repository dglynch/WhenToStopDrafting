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

import java.util.Arrays;

public enum Rarity {
    TOKEN(0),
    BASIC_LAND(1),
    COMMON(2),
    UNCOMMON(3),
    RARE(4),
    MYTHIC_RARE(5),
    ;

    private final int rarity;

    Rarity(int rarity) {
        this.rarity = rarity;
    }

    public static Rarity fromInt(int rarity) {
        return Arrays.stream(values())
                .filter(value -> value.rarity == rarity)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
