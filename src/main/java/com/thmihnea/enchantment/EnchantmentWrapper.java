package com.thmihnea.enchantment;

import com.thmihnea.action.Action;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class EnchantmentWrapper extends Enchantment implements Runnable {

    private String name;
    private int maxLevel;
    private int startLevel;
    private int id;
    private EnchantmentTarget enchantmentTarget;
    private List<Enchantment> conflicts;
    private List<ItemStack> targets;
    private List<Action> actions;
    private boolean runnable;

    public EnchantmentWrapper(int id, String name, int startLevel, int maxLevel, EnchantmentTarget enchantmentTarget, List<Enchantment> conflicts, List<ItemStack> targets, List<Action> actions, boolean runnable) {
        super(id);
        this.id = id;
        this.name = name;
        this.maxLevel = maxLevel;
        this.startLevel = startLevel;
        this.enchantmentTarget = enchantmentTarget;
        this.conflicts = conflicts;
        this.targets = targets;
        this.runnable = runnable;
        this.actions = actions;
    }

    public boolean isRunnable() {
        return this.runnable;
    }

    public List<Action> getActions() {
        return this.actions;
    }

    public List<Enchantment> getConflicts() {
        return this.conflicts;
    }

    public List<ItemStack> getTargets() {
        return this.targets;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getStartLevel() {
        return this.startLevel;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return this.enchantmentTarget;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return this.conflicts.contains(enchantment);
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        //return this.targets.contains(itemStack);
        return true;
    }

    @Override
    public abstract void run();

    public abstract void performEvent(Player player, Event event);

    public abstract void perform(Player player);
}
