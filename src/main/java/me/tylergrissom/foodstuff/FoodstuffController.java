package me.tylergrissom.foodstuff;

import me.tylergrissom.foodstuff.utility.ColorUtility;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;
import java.util.logging.Level;

/**
 * Copyright Tyler Grissom 2018
 */
public class FoodstuffController {

    public ItemStack createItem(ConfigurationSection section) {
        Material material = Material.valueOf(section.getString("material").toUpperCase());
        int amount = section.getInt("amount", 1);
        byte data = (byte) section.getInt("data", 0);
        short damage = (short) section.getInt("durability", 0);
        boolean unbreakable = section.getBoolean("unbreakable", false);

        ItemStack is = new ItemStack(material, amount, damage, data);
        ItemMeta meta = is.getItemMeta();

        meta.setUnbreakable(unbreakable);

        if (section.get("display_name") != null) {
            String name = ColorUtility.translate(section.getString("display_name"));

            meta.setDisplayName(name);
        }

        if (section.get("lore") != null) {
            List<String> originalLore = section.getStringList("lore");
            List<String> lore = new ArrayList<>();

            for (String str : originalLore) {
                lore.add(ColorUtility.translate(str));
            }

            meta.setLore(lore);
        }

        meta.setUnbreakable(unbreakable);

        if (section.get("flags") != null) {
            Set<ItemFlag> flags = new HashSet<>();

            for (String flag : section.getStringList("flags")) {
                flags.add(ItemFlag.valueOf(flag.toUpperCase()));
            }

            meta.addItemFlags((ItemFlag[]) flags.toArray(new ItemFlag[]{}));
        }

        is.setItemMeta(meta);

        if (section.getConfigurationSection("enchantments") != null) {
            Map<Enchantment, Integer> enchantments = new HashMap<>();

            for (String key : section.getConfigurationSection("enchantments").getKeys(false)) {
                enchantments.put(Enchantment.getByName(key.toUpperCase()), section.getConfigurationSection("enchantments").getInt(key, 1));
            }

            is.addUnsafeEnchantments(enchantments);
        }

        if (section.get("color") != null) {
            if (material == Material.LEATHER_HELMET || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_BOOTS) {
                String[] split = section.getString("color").split(",");

                try {
                    int r = Integer.parseInt(split[0]);
                    int g = Integer.parseInt(split[1]);
                    int b = Integer.parseInt(split[2]);

                    LeatherArmorMeta leatherMeta = (LeatherArmorMeta) is.getItemMeta();

                    leatherMeta.setColor(Color.fromRGB(r, g, b));

                    is.setItemMeta(leatherMeta);
                } catch (NumberFormatException ignored) {
                    Bukkit.getLogger().log(Level.WARNING, "Invalid RGB color format (Ex. '255,0,0' for red)");
                }
            }
        }

        return is;
    }
}
