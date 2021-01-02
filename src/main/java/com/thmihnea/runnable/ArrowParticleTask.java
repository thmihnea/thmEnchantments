package com.thmihnea.runnable;

import com.thmihnea.EnchantmentsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitTask;
import org.inventivetalent.particle.ParticleEffect;

public class ArrowParticleTask implements Runnable {

    private Projectile projectile;
    private BukkitTask task;
    private ParticleEffect effect = ParticleEffect.valueOf(EnchantmentsPlugin.cfg.getString("actions.three-shot-particle"));

    public ArrowParticleTask(Projectile projectile) {
        this.projectile = projectile;
        task = Bukkit.getScheduler().runTaskTimer(EnchantmentsPlugin.getInstance(), this, 0L, 1L);
    }

    @Override
    public void run() {
        if (this.projectile.isOnGround()) {
            this.clear();
        } else {
            this.effect.send(Bukkit.getOnlinePlayers(), this.projectile.getLocation(), 0, 0, 0, 0, 1);
        }
    }

    public void clear() {
        if (task != null) {
            Bukkit.getScheduler().cancelTask(task.getTaskId());
            task = null;
        }
    }
}
