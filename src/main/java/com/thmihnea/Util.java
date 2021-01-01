package com.thmihnea;

import com.thmihnea.action.StatTrakPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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

    public static void registerEvents() {
        EnchantmentsPlugin.getInstance().events = Arrays.asList(
            new StatTrakPlayer()
        );
        EnchantmentsPlugin.getInstance().events.forEach(listener -> {
            Bukkit.getPluginManager().registerEvents(listener, EnchantmentsPlugin.getInstance());
        });
    }
}
