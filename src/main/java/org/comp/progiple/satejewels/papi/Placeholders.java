package org.comp.progiple.satejewels.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import org.comp.progiple.satejewels.api.SJAPI;
import org.comp.progiple.satejewels.configuration.Config;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {
    private final Config config = Config.getConfig();

    @Override
    public @NotNull String getIdentifier() {
        return "satejewels";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ProGiple";
    }

    @Override
    public @NotNull String getVersion() {
        return "latest";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String mainPath = "placeholders.autoName.%s";
        int value = SJAPI.getJewels(player);

        if (params.equalsIgnoreCase("autoname")) {
            byte lastNum = (byte) (value % 10);
            return switch (lastNum) {
                case 0 -> SJAPI.getJewelName(config.getString(String.format(mainPath, "0")));
                case 1 -> SJAPI.getJewelName(config.getString(String.format(mainPath, "1")));
                case 2, 3, 4 -> SJAPI.getJewelName(config.getString(String.format(mainPath, "2-4")));
                default -> SJAPI.getJewelName(config.getString(String.format(mainPath, "5+")));
            };
        }
        else if (params.contains("name_")) {
            String[] massive = params.split("_");
            if (massive.length >= 2) {
                return SJAPI.getJewelName(massive[1]);
            }
        }
        else if (params.equalsIgnoreCase("balance")) {
            return String.valueOf(value);
        }
        return null;
    }
}
