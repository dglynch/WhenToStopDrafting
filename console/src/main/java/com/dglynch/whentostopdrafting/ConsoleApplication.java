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

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleApplication {
    private static final String DEFAULT_PLAYER_LOG_FILE_PATH =
            System.getProperty("user.home") + "/AppData/LocalLow/Wizards Of The Coast/MTGA/Player.log";
    private static final String DEFAULT_DATA_FILE_PATH_PREFIX =
            System.getenv("ProgramFiles") + "/Wizards of the Coast/MTGA/MTGA_Data/Downloads/Data/";
    private static final int CARDS_PER_PLAYSET = 4;
    private static final double EXPECTED_MYTHIC_RATE_ZNR = 1.0 / 7.4;
    private static final double EXPECTED_WILDCARD_RATE = 1.0 / 30;
    private static final double EXPECTED_RARE_CARD_RATE_ZNR = 1.0 - EXPECTED_MYTHIC_RATE_ZNR - EXPECTED_WILDCARD_RATE;

    public static void main(String[] args) {
        String playerLogFilePath = Arrays.stream(args).findFirst().orElse(DEFAULT_PLAYER_LOG_FILE_PATH);
        String dataFilePathPrefix = Arrays.stream(args).skip(1).findFirst().orElse(DEFAULT_DATA_FILE_PATH_PREFIX);

        try {
            DownloadsDataFileFinder downloadsDataFileFinder = new DownloadsDataFileFinder(dataFilePathPrefix);

            String localizationDataFilePath = downloadsDataFileFinder.findFilePath("loc");
            LocalizationDataParser localizationDataParser = new LocalizationDataParser(localizationDataFilePath);
            Map<Integer, String> localization = localizationDataParser.readLocalization();

            String cardsDataFilePath = downloadsDataFileFinder.findFilePath("cards");
            CardsDataParser cardsDataParser = new CardsDataParser(cardsDataFilePath, localization);
            Map<Integer, Card> cards = cardsDataParser.readCards();

            CollectionParser collectionParser = new CollectionParser(playerLogFilePath);
            Map<Card, Integer> collection = collectionParser.readCollection().entrySet().stream()
                    .collect(Collectors.toMap(entry -> cards.get(Integer.valueOf(entry.getKey())), Map.Entry::getValue));

            InventoryParser inventoryParser = new InventoryParser(playerLogFilePath);
            Map<String, Integer> inventory = inventoryParser.readInventory();

            if (collection.isEmpty()) {
                System.out.println("No collection data found at " + playerLogFilePath);
            } else {
                long numberOfAvailableRaresInZnr = cards.entrySet().stream()
                        .filter(entry -> entry.getValue().getRarity().equals(Rarity.RARE))
                        .filter(entry -> entry.getValue().getSet().equals("ZNR"))
                        .filter(entry -> entry.getValue().isBoosterAvailable())
                        .count() * CARDS_PER_PLAYSET;

                int numberOfCollectedRaresInZnr = collection.entrySet().stream()
                        .filter(entry -> entry.getKey().getRarity().equals(Rarity.RARE))
                        .filter(entry -> entry.getKey().getSet().equals("ZNR"))
                        .filter(entry -> entry.getKey().isBoosterAvailable())
                        .mapToInt(Map.Entry::getValue)
                        .sum();

                System.out.println("You have collected " + numberOfCollectedRaresInZnr + " of " +
                        numberOfAvailableRaresInZnr + " rares in ZNR.");

                System.out.println("You have " + inventory.get("ZNR") + " unopened booster packs of ZNR.");

                System.out.printf("You can expect to open %.1f more rares from your booster packs.%n",
                        inventory.get("ZNR") * EXPECTED_RARE_CARD_RATE_ZNR);
            }
        } catch (IOException e) {
            System.out.println("Failed to find required data files at " + dataFilePathPrefix);
        }
    }
}
