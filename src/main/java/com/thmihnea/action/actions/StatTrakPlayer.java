package com.thmihnea.action.actions;

import com.thmihnea.action.Action;
import com.thmihnea.enchantment.Enchantment;
import com.thmihnea.enchantment.EnchantmentManager;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StatTrakPlayer extends Action {

    public StatTrakPlayer() {
        super(false);
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
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (!(damager instanceof Player) && !(damager instanceof Arrow)) return;
        Entity damaged = e.getEntity();
        if (!(damaged instanceof Player)) return;
        Player target = ((Player) damaged).getPlayer();
        Entity entity = e.getDamager();
        Player player;
        if (entity instanceof Arrow) {
            if (!(((Arrow) entity).getShooter() instanceof Player)) return;
            player = (Player) ((Arrow) entity).getShooter();
        }
        else player = (Player) entity;
        ItemStack itemStack = player.getItemInHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;
        AtomicBoolean hasAction = new AtomicBoolean(false);
        itemStack.getEnchantments().keySet().forEach(enchantment -> {
            if (!EnchantmentManager.getEnchantments().containsKey(enchantment.getId())) return;
            Enchantment ench = EnchantmentManager.getEnchantment(enchantment.getId());
            ench.getActions().forEach(action -> {
                if (action instanceof StatTrakPlayer) hasAction.set(true);
            });
        });
        if (!hasAction.get()) return;
        if (target.getHealth() - e.getDamage() > 0) return;
        NBTItem nbt = new NBTItem(itemStack);
        if (!nbt.hasKey("stat-trak-player")) nbt.setInteger("stat-trak-player", 0);
        nbt.setInteger("stat-trak-player", nbt.getInteger("stat-trak-player") + 1);
        ItemStack item = nbt.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        List<String> newLore = new ArrayList<>();
        if (lore != null) {
            lore.forEach(str -> {
                if (str.contains("Stat Trak"))
                    return;
                newLore.add(str);
            });
        }
        newLore.add(ChatColor.translateAlternateColorCodes('&', "&eStat Trak (Players): &c" + nbt.getInteger("stat-trak-player")));
        meta.setLore(newLore);
        itemStack.setItemMeta(meta);
        player.updateInventory();
    }

    @Override
    public void onArrowShoot(EntityShootBowEvent e) {
        return;
    }

    @Override
    public void run() {
        return;
    }
}
