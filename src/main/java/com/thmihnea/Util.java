package com.thmihnea;

import com.thmihnea.action.Action;
import com.thmihnea.action.ActionType;
import com.thmihnea.action.actions.Earthquake;
import com.thmihnea.action.actions.StatTrakPlayer;
import com.thmihnea.action.actions.ThreeShot;
import com.thmihnea.action.actions.Wings;
import com.thmihnea.enchantment.Enchantment;
import com.thmihnea.enchantment.EnchantmentHandler;
import com.thmihnea.enchantment.EnchantmentManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.EnchantmentTarget;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Util {

    public static void setupFiles() {
        File dir = new File("plugins", "thmEnchantments");
        if (!dir.exists()) dir.mkdir();
        if (!EnchantmentsPlugin.config.exists()) EnchantmentsPlugin.getInstance().saveDefaultConfig();
        try {
            EnchantmentsPlugin.cfg.load(EnchantmentsPlugin.config);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void setupEnchants() {
        EnchantmentsPlugin.cfg.getConfigurationSection("enchants").getKeys(false).forEach(key -> {
            List<Action> actions = new ArrayList<>();
            List<String> actionStrings = EnchantmentsPlugin.cfg.getStringList("enchants." + key + ".actions");
            actionStrings.forEach(str -> actions.add(ActionType.getFromConfigField(str).getAction()));
            AtomicBoolean runnable = new AtomicBoolean(false);
            actions.forEach(action -> {
                if (action.isRunnable()) runnable.set(true);
            });
            int id = EnchantmentsPlugin.cfg.getInt("enchants." + key + ".id");
            int startLevel = EnchantmentsPlugin.cfg.getInt("enchants." + key + ".startLevel");
            int maxLevel = EnchantmentsPlugin.cfg.getInt("enchants." + key + ".maxLevel");
            String name = EnchantmentsPlugin.cfg.getString("enchants." + key + ".name");
            EnchantmentTarget enchantmentTarget = EnchantmentTarget.valueOf(EnchantmentsPlugin.cfg.getString("enchants." + key + ".enchantmentTarget"));
            Enchantment enchantment = new Enchantment(id, name, startLevel, maxLevel, enchantmentTarget, Collections.emptyList(), actions, runnable.get());
            EnchantmentManager.addEnchantment(enchantment);
        });
    }

    public static void registerEvents() {
        EnchantmentsPlugin.getInstance().events = Arrays.asList(
            new StatTrakPlayer(),
            new ThreeShot(),
            new Earthquake(),
            new Wings()
        );
        EnchantmentsPlugin.getInstance().events.forEach(listener -> {
            Bukkit.getPluginManager().registerEvents(listener, EnchantmentsPlugin.getInstance());
        });
    }
}
