package com.thmihnea;

import com.thmihnea.action.StatTrakPlayer;
import com.thmihnea.enchantment.Enchantment;
import com.thmihnea.enchantment.EnchantmentHandler;
import com.thmihnea.enchantment.EnchantmentManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class EnchantmentsPlugin extends JavaPlugin {

    private static EnchantmentsPlugin instance;
    private long startTime;
    public static File config = new File("plugins/thmEnchantments/config.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
    public List<Listener> events = new ArrayList<>();

    @Override
    public void onEnable() {
        this.startTime = System.currentTimeMillis();
        getLogger().log(Level.INFO, "Attempting to enable plugin modules.");
        instance = this;
        Util.setupFiles();
        Util.registerEvents();
        Enchantment stattrak = new Enchantment(105, "Stat Trak", 0, 5, EnchantmentTarget.WEAPON, Collections.emptyList(), Arrays.asList(new ItemStack(Material.DIAMOND_SWORD)), Collections.singletonList(new StatTrakPlayer()), false);
        EnchantmentManager.addEnchantment(stattrak);
        EnchantmentHandler.register();
        for (Player player : Bukkit.getOnlinePlayers())
            player.getItemInHand().addUnsafeEnchantment(stattrak, 1);
        getLogger().log(Level.INFO, "Plugin was successfully enabled. Process took: " + (System.currentTimeMillis() - this.startTime) + "ms");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "[thmEnchantments] Plugin was successfully disabled. Goodbye.");
    }

    public static EnchantmentsPlugin getInstance() {
        return instance;
    }
}
