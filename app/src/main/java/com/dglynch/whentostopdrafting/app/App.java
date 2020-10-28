package com.dglynch.whentostopdrafting.app;

import com.dglynch.whentostopdrafting.log.LogParser;

import java.util.Map;

public class App {
    private static final String PLAYER_LOG_FILE_PATH =
            System.getProperty("user.home") + "/AppData/LocalLow/Wizards Of The Coast/MTGA/Player.log";

    public static void main(String[] args) {
        LogParser logParser = new LogParser(PLAYER_LOG_FILE_PATH);
        Map<String, Integer> collection = logParser.readCollection();
        if (collection.isEmpty()) {
            System.out.println("No collection data found at " + PLAYER_LOG_FILE_PATH);
        } else {
            collection.forEach((key, value) -> System.out.println(value + " " + key));
        }
    }
}
