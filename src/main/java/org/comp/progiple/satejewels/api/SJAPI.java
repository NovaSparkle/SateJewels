package org.comp.progiple.satejewels.api;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.comp.progiple.satejewels.configuration.Config;
import org.comp.progiple.satejewels.configuration.DataConfig;

import java.util.Map;
import java.util.UUID;

public class SJAPI {
    private final static DataConfig cfg = DataConfig.getDataConfig();

    public static int getJewels(String nick) {
        return cfg.getValue(nick);
    }

    public static int getJewels(OfflinePlayer offlinePlayer) {
        return SJAPI.getJewels(offlinePlayer.getName());
    }

    public static int getJewels(UUID uuid) {
        return SJAPI.getJewels(Bukkit.getOfflinePlayer(uuid));
    }

    public static void giveJewels(String nick, int value) {
        cfg.setValue(nick, SJAPI.getJewels(nick) + value);
    }

    public static void giveJewels(OfflinePlayer offlinePlayer, int value) {
        SJAPI.giveJewels(offlinePlayer.getName(), value);
    }

    public static void giveJewels(UUID uuid, int value) {
        SJAPI.giveJewels(Bukkit.getOfflinePlayer(uuid), value);
    }

    public static void removeJewels(String nick, int value) {
        cfg.setValue(nick, SJAPI.getJewels(nick) - value);
    }

    public static void removeJewels(OfflinePlayer offlinePlayer, int value) {
        SJAPI.removeJewels(offlinePlayer.getName(), value);
    }

    public static void removeJewels(UUID uuid, int value) {
        SJAPI.removeJewels(Bukkit.getOfflinePlayer(uuid), value);
    }

    public static boolean payJewels(String fromPlayerNick, String toPlayerNick, int value) {
        if (SJAPI.getJewels(fromPlayerNick) >= value) {
            SJAPI.removeJewels(fromPlayerNick, value);
            SJAPI.giveJewels(toPlayerNick, value);
            return true;
        }
        return false;
    }

    public static void setJewels(String nick, int value) {
        cfg.setValue(nick, value);
    }

    public static void setJewels(OfflinePlayer offlinePlayer, int value) {
        SJAPI.setJewels(offlinePlayer.getName(), value);
    }

    public static void setJewels(UUID uuid, int value) {
        SJAPI.setJewels(Bukkit.getOfflinePlayer(uuid), value);
    }

    public static ConfigurationSection getPlayerSection() {
        return cfg.getConfig().getConfigurationSection("players");
    }

    public static Map<String, String> getJewelNames() {
        return Config.getJewelsNames();
    }

    public static String getJewelName(String id) {
        return SJAPI.getJewelNames().get(id);
    }
}
