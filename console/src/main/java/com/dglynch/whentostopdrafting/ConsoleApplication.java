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
import java.util.Map;

public class ConsoleApplication {
    private static final String DEFAULT_PLAYER_LOG_FILE_PATH =
            System.getProperty("user.home") + "/AppData/LocalLow/Wizards Of The Coast/MTGA/Player.log";

    public static void main(String[] args) {
        String playerLogFilePath = Arrays.stream(args).findFirst().orElse(DEFAULT_PLAYER_LOG_FILE_PATH);
        LogParser logParser = new LogParser(playerLogFilePath);

        Map<String, Integer> collection = logParser.readCollection();
        if (collection.isEmpty()) {
            System.out.println("No collection data found at " + playerLogFilePath);
        } else {
            collection.forEach((key, value) -> System.out.println(value + " " + key));
        }
    }
}
