package me.julius.mc2fa;

import me.julius.mc2fa.commands.AuthCommand;
import org.bukkit.*;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.builder.Diff;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static final String generatorSettings = "{\"biome\":\"minecraft:plains\",\"layers\":[{\"block\":\"minecraft:white_wool\",\"height\":1}]}";

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private World qrWorld;

    public World getQrWorld() {
        return qrWorld;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.getCommand("2fa").setExecutor(new AuthCommand());

        Bukkit.getPluginManager().registerEvents(new PluginEventListener(), this);

        System.out.println("");
        if (Bukkit.getWorld("qrcode-authentication") == null) {
            WorldCreator wg = new WorldCreator("Build").type(WorldType.FLAT).
                    generatorSettings(generatorSettings).
                    generateStructures(false);
            qrWorld = wg.createWorld();
            qrWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            qrWorld.setDifficulty(Difficulty.PEACEFUL);
        }
    }

}
