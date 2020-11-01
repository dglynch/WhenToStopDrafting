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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Card {

    private final int id;

    @NotNull
    private final String name;

    @NotNull
    private final String set;

    @NotNull
    private final Rarity rarity;

    public Card(int id, @NotNull String name, @NotNull String set, @NotNull Rarity rarity) {
        this.id = id;
        this.name = name;
        this.set = set;
        this.rarity = rarity;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getSet() {
        return set;
    }

    public @NotNull Rarity getRarity() {
        return rarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id &&
                name.equals(card.name) &&
                set.equals(card.set) &&
                rarity == card.rarity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, set, rarity);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", set='" + set + '\'' +
                ", rarity=" + rarity +
                '}';
    }
}
