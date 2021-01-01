package com.thmihnea.enchantment;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentManager {

    private static Map<Integer, Enchantment> enchantments = new HashMap<>();

    public static Map<Integer, Enchantment> getEnchantments() {
        return enchantments;
    }

    public static void addEnchantment(Enchantment enchantment) {
        enchantments.put(enchantment.getId(), enchantment);
    }

    public static void removeEnchantment(int id) {
        enchantments.remove(id);
    }

    public static void removeEnchantment(Enchantment enchantment) {
        removeEnchantment(enchantment.getId());
    }

    public static Enchantment getEnchantment(int id) {
        return enchantments.get(id);
    }

}
