package com.thmihnea.action.actions;

import com.thmihnea.action.Action;
import com.thmihnea.enchantment.Enchantment;
import com.thmihnea.enchantment.EnchantmentManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Earthquake extends Action {

    public Earthquake() {
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
        if (!(e.getDamager() instanceof Player) && !(e.getDamager() instanceof Arrow)) return;
        if (!(e.getEntity() instanceof LivingEntity)) return;
        Entity entity = e.getDamager();
        Player player;
        if (entity instanceof Arrow) {
            if (!(((Arrow) entity).getShooter() instanceof Player)) return;
            player = (Player) ((Arrow) entity).getShooter();
        }
        else player = (Player) entity;
        AtomicBoolean hasEarthquake = new AtomicBoolean(false);
        player.getItemInHand().getEnchantments().keySet().forEach(enchantment -> {
            if (!EnchantmentManager.getEnchantments().containsKey(enchantment.getId())) return;
            Enchantment ench = EnchantmentManager.getEnchantment(enchantment.getId());
            ench.getActions().forEach(action -> {
                if (action instanceof Earthquake) hasEarthquake.set(true);
            });
        });
        if (!hasEarthquake.get()) return;
        Location location = e.getEntity().getLocation().subtract(0, -1, 0);
        double theta = 0;
        double r = 0.5;
        for (int x = location.getBlockX(); x >= location.getBlockX() - 2; x--) {
            for (int z = location.getBlockZ(); z >= location.getBlockZ() - 2; z--) {
                for (int y = location.getBlockY(); y >= location.getBlockY() - 2; y--){
                    Block block = new Location(player.getWorld(), x, y, z).getBlock();
                    if (block.getType() == Material.AIR) continue;
                    Material material = block.getType();
                    block.setType(Material.AIR);
                    FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(block.getLocation(), material, (byte) 0);
                    fallingBlock.setVelocity(new Vector(r * Math.cos(theta), 1.75, r * Math.sin(theta)));
                    theta += Math.PI/6;
                }
            }
        }
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
