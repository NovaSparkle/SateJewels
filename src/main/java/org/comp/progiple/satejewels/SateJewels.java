package org.comp.progiple.satejewels;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.comp.progiple.satejewels.configuration.Config;
import org.comp.progiple.satejewels.configuration.DataConfig;
import org.comp.progiple.satejewels.papi.Placeholders;

import java.util.Objects;

public final class SateJewels extends JavaPlugin {

    @Getter
    private static SateJewels plugin;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        Config.setConfig(new Config());
        DataConfig.setDataConfig(new DataConfig());

        Command command = new Command();
        Objects.requireNonNull(getCommand("satejewels")).setTabCompleter(command);
        Objects.requireNonNull(getCommand("satejewels")).setExecutor(command);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Placeholders placeholders = new Placeholders();
            placeholders.register();
        }
    }

    @Override
    public void onDisable() {}
}
