package com.thmihnea.enchantment;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class EnchantmentHandler {

    public static void register() {
        EnchantmentManager.getEnchantments().forEach((id, enchantment) -> {
            boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment);
            if (!registered) {
                Bukkit.getLogger().log(Level.INFO, "[thmEnchantments] Attempting to register enchantment " + enchantment.getName() + ". (ID: " + id + ")");
                registerEnchantments(enchantment);
            }
        });
    }

    public static void registerEnchantments(Enchantment enchantment) {
        boolean registered = true;
        long start = System.currentTimeMillis();
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[thmEnchantments] Enchantment " + enchantment.getName() + " has failed registration. Please report the stacktrace mentioned above!");
        }
        if (registered) {
            Bukkit.getLogger().log(Level.INFO, "[thmEnchantments] Successfully registed enchantment " + enchantment.getName() + ". Process took: " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
