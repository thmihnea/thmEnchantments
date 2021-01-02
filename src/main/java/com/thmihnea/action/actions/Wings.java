package com.thmihnea.action.actions;

import com.thmihnea.action.Action;
import com.thmihnea.enchantment.Enchantment;
import com.thmihnea.enchantment.EnchantmentManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Wings extends Action {
    public Wings() {
        super(true);
    }

    @Override
    public void onBlockBreak(BlockBreakEvent e) {
        return;
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        return;
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent e) {
        return;
    }

    @Override
    public void onArrowShoot(EntityShootBowEvent e) {
        return;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getGameMode().equals(GameMode.CREATIVE)) {
                player.setAllowFlight(true);
                return;
            }
            boolean hasWings = hasWings(player);
            player.setAllowFlight(hasWings);
        });
    }

    public boolean hasWings(Player player) {
        boolean hasWings = true;
        for (ItemStack item : player.getInventory().getArmorContents()) {
            AtomicBoolean hasAction = new AtomicBoolean(false);
            item.getEnchantments().keySet().forEach(enchantment -> {
                if (!EnchantmentManager.getEnchantments().containsKey(enchantment.getId())) return;
                Enchantment ench = EnchantmentManager.getEnchantment(enchantment.getId());
                ench.getActions().forEach(action -> {
                    if (action instanceof Wings) hasAction.set(true);
                });
            });
            if (!hasAction.get()) hasWings = false;
        }
        return hasWings;
    }
}
