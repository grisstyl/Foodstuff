package me.tylergrissom.foodstuff.utility;

import org.bukkit.ChatColor;

/**
 * Copyright Tyler Grissom 2018
 */
public class ColorUtility {

    public static String translate(String str) {
        return ChatColor.translateAlternateColorCodes('&',  str);
    }
}
