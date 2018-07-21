package me.tylergrissom.foodstuff;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright Tyler Grissom 2018
 */
public class FoodstuffPlugin extends JavaPlugin {

    @Getter
    private FoodstuffPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
