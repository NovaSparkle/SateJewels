package org.comp.progiple.satejewels.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.comp.progiple.satejewels.api.SJAPI;
import org.comp.progiple.satejewels.configuration.Config;

import java.util.Map;

public class Tools {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendMessage(CommandSender sender, String messageId, String soundId) {
        Tools.sendMessage(sender, messageId, 0, "", soundId);
    }

    public static void sendMessage(CommandSender sender, String message, String soundId, boolean value) {
        if (sender == null) return;
        if (message.contains("{name_")) {
            for (Map.Entry<String, String> entry : SJAPI.getJewelNames().entrySet()) {
                message = message.replace(String.format("{name_%s}", entry.getKey()), entry.getValue());
            }
        }

        sender.sendMessage(Tools.color(message));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player.getLocation(), Config.getConfig().getSound(soundId), 1, 1);
        }
    }

    public static void sendMessage(CommandSender sender, String messageId, int amount, String getterNick, String soundId) {
        String message = Config.getConfig().getString(String.format("messages.%s", messageId));
        message = message.replace("{amount}", String.valueOf(amount));
        message = message.replace("{player}", getterNick);
        Tools.sendMessage(sender, message, soundId, true);
    }
}
