package com.thmihnea.action;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class Action implements Listener {

    private boolean runnable;

    public Action(boolean runnable) {
        this.runnable = runnable;
    }

    public boolean isRunnable() {
        return this.runnable;
    }

    @EventHandler
    public abstract void onBlockBreak(BlockBreakEvent e);

    @EventHandler
    public abstract void onInteract(PlayerInteractEvent e);

    @EventHandler
    public abstract void onDamage(EntityDamageByEntityEvent e);

    @EventHandler
    public abstract void onArrowShoot(EntityShootBowEvent e);

    public abstract void run();
}
