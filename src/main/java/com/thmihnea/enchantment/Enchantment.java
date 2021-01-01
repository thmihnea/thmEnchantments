package com.thmihnea.enchantment;

import com.thmihnea.EnchantmentsPlugin;
import com.thmihnea.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Enchantment extends EnchantmentWrapper {

    public Enchantment(int id, String name, int startLevel, int maxLevel, EnchantmentTarget enchantmentTarget, List<org.bukkit.enchantments.Enchantment> conflicts, List<ItemStack> targets, List<Action> actions, boolean runnable) {
        super(id, name, startLevel, maxLevel, enchantmentTarget, conflicts, targets, actions, runnable);
        if (this.isRunnable()) {
            Bukkit.getScheduler().runTaskTimer(EnchantmentsPlugin.getInstance(), this, 0L, 20L);
        }
    }

    @Override
    public void run() {
        this.getActions().forEach(action -> {
            if (action.isRunnable()) action.run();
        });
    }

    @Override
    public void performEvent(Player player, Event event) {

    }

    @Override
    public void perform(Player player) {

    }
}
