package org.comp.progiple.satejewels;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.comp.progiple.satejewels.api.SJAPI;
import org.comp.progiple.satejewels.configuration.Config;
import org.comp.progiple.satejewels.configuration.DataConfig;
import org.comp.progiple.satejewels.utils.Tools;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length >= 1) {
            switch (args[0]) {
                case "give":
                case "add":
                    if (hasAdminPerm(sender)) {
                        if (args.length >= 3) {
                            String nick = args[1];
                            int value = Integer.parseInt(args[2]);

                            SJAPI.giveJewels(nick, value);
                            Tools.sendMessage(sender, "giveJewels", value, nick, "successful");
                        }
                        else this.noArgsMess(sender);
                    }
                    break;
                case "reload":
                    if (hasAdminPerm(sender)) {
                        Config.getConfig().reload();
                        DataConfig.getDataConfig().reload();
                        Tools.sendMessage(sender, "reloadPlugin", "successful");
                    }
                    break;
                case "balance":
                    if (args.length >= 2) {
                        String nick = args[1];
                        if (sender.hasPermission("lmjewels.balance.another")) {
                            Tools.sendMessage(sender, "playerBalance", DataConfig.getDataConfig().getValue(nick), nick, "successful");
                        }
                        else Tools.sendMessage(sender, "noPerm", "error");
                    }
                    else {
                        String nick = sender.getName();
                        if (sender.hasPermission("lmjewels.balance")) {
                            Tools.sendMessage(sender, "balance", DataConfig.getDataConfig().getValue(nick), "", "successful");
                        }
                        else Tools.sendMessage(sender, "noPerm", "error");
                    }
                    break;
                case "take":
                    if (hasAdminPerm(sender)) {
                        if (args.length >= 3) {
                            String nick = args[1];
                            int value = Integer.parseInt(args[2]);

                            SJAPI.removeJewels(nick, value);
                            Tools.sendMessage(sender, "removeJewels", value, nick, "successful");
                        }
                        else this.noArgsMess(sender);
                    }
                    break;
                case "pay":
                    if (sender.hasPermission("lmjewels.pay")) {
                        if (args.length >= 3) {
                            String nick = args[1];
                            int value = Integer.parseInt(args[2]);

                            if (SJAPI.payJewels(sender.getName(), nick, value)) {
                                Tools.sendMessage(sender, "payJewels", value, nick, "successful");
                                Tools.sendMessage(Bukkit.getPlayer(nick), "payedJewels", value, sender.getName(), "successful");
                            }
                            else Tools.sendMessage(sender, "noJewels", "error");
                        }
                        else this.noArgsMess(sender);
                    }
                    else Tools.sendMessage(sender, "noPerm", "error");
                    break;
                case "set":
                    if (hasAdminPerm(sender)) {
                        if (args.length >= 3) {
                            String nick = args[1];
                            int value = Integer.parseInt(args[2]);

                            SJAPI.setJewels(nick, value);
                            Tools.sendMessage(sender, "payJewels", value, nick, "successful");
                        }
                        else this.noArgsMess(sender);
                    }
                    break;
            }
        }
        else this.noArgsMess(sender);
        return true;
    }

    private void noArgsMess(CommandSender sender) {
        Tools.sendMessage(sender, "noArgs", "error");
    }

    private boolean hasAdminPerm(CommandSender sender) {
        boolean value = sender.hasPermission("lmjewels.admin");;
        if (!value) Tools.sendMessage(sender, "noPerm", "error");
        return value;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            list.addAll(Arrays.asList("add", "reload", "take", "set", "pay", "balance"));
        }
        else if (strings.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }
        else if (strings.length == 3) {
            list.add("<кол-во>");
        }
        return list;
    }
}
