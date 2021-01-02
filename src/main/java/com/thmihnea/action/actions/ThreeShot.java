package com.thmihnea.action.actions;

import com.thmihnea.EnchantmentsPlugin;
import com.thmihnea.action.Action;
import com.thmihnea.enchantment.Enchantment;
import com.thmihnea.enchantment.EnchantmentManager;
import com.thmihnea.runnable.ArrowParticleTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ThreeShot extends Action {

    public ThreeShot() {
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
    public void onDamage(EntityDamageByEntityEvent e) {
        return;
    }

    private static Map<Player, ItemStack> usedArrows = new HashMap<>();

    @Override
    @EventHandler
    public void onArrowShoot(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = ((Player) e.getEntity()).getPlayer();
        int j = player.getInventory().first(Material.ARROW);
        if (j == -1) return;
        ItemStack usedArrow = player.getInventory().getItem(j);
        if (usedArrow == null || usedArrow.getType() == Material.AIR) return;
        AtomicBoolean hasThreeShot = new AtomicBoolean(false);
        e.getBow().getEnchantments().keySet().forEach(enchantment -> {
            if (!EnchantmentManager.getEnchantments().containsKey(enchantment.getId())) return;
            Enchantment ench = EnchantmentManager.getEnchantment(enchantment.getId());
            ench.getActions().forEach(action -> {
                if (action instanceof ThreeShot) hasThreeShot.set(true);
            });
        });
        if (!hasThreeShot.get()) return;
        AtomicBoolean hasBlasterArrows = new AtomicBoolean(false);
        AtomicReference<ItemStack> is = new AtomicReference<>(new ItemStack(Material.AIR));
        Arrays.asList(player.getInventory().getContents()).forEach(item -> {
            if (item == null || item.getType() == Material.AIR) return;
            if (item.getType() == Material.ARROW &&
                    item.hasItemMeta() &&
                    item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cBlaster Arrow"))) {
                hasBlasterArrows.set(true);
                is.set(new ItemStack(item));
            }
        });
        if (!hasBlasterArrows.get()) return;
        ItemStack itemStack = is.get(); // at this point this should already be different than Material.AIR since we found that the user has blaster arrows
        Arrays.asList(player.getInventory().getContents()).forEach(i -> {
            if (i == null || i.getType() == Material.AIR) return;
            if (i.equals(itemStack)) {
                if (i.getAmount() > 1) i.setAmount(i.getAmount() - 1);
                else {
                    int first = player.getInventory().first(itemStack);
                    player.getInventory().clear(first);
                }
            }
        });
        /*if (!usedArrow.hasItemMeta()) Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentsPlugin.getInstance(), () -> {
            usedArrow.setAmount(usedArrow.getAmount() + 1);
        }, 5L);
        else {
            if (!usedArrow.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&cBlaster Arrow")))
                Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantmentsPlugin.getInstance(), () -> {
                    usedArrow.setAmount(usedArrow.getAmount() + 1);
                }, 5L);
        }*/
        e.getEntity().getWorld().spawnArrow(e.getProjectile().getLocation(), rotateVector(e.getProjectile().getVelocity(), 0.2), e.getForce() * 2, 0f).setShooter(player);
        e.getEntity().getWorld().spawnArrow(e.getProjectile().getLocation(), rotateVector(e.getProjectile().getVelocity(), -0.2), e.getForce() * 2,0f).setShooter(player);
        new ArrowParticleTask((Projectile) e.getProjectile());
    }

    public Vector rotateVector(Vector vector, double whatAngle) {
        double sin = Math.sin(whatAngle);
        double cos = Math.cos(whatAngle);
        double x = vector.getX() * cos + vector.getZ() * sin;
        double z = vector.getX() * -sin + vector.getZ() * cos;

        return vector.setX(x).setZ(z);
    }

    @Override
    public void run() {
        return;
    }
}
