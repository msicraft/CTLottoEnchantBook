package me.msicraft.ctlottoenchantbook;

import me.msicraft.ctlottoenchantbook.Command.MainCommand;
import me.msicraft.ctlottoenchantbook.Command.SubCommand;
import me.msicraft.ctlottoenchantbook.Event.LottoEnchantBookEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CTLottoEnchantBook extends JavaPlugin {

    private static CTLottoEnchantBook plugin;

    public static CTLottoEnchantBook getPlugin() {
        return plugin;
    }

    public static final String PREFIX = ChatColor.GREEN + "[CTLottoEnchantBook]";

    private LottoEnchantBookManager lottoEnchantBookManager;

    @Override
    public void onEnable() {
        plugin = this;
        createConfigFiles();

        lottoEnchantBookManager = new LottoEnchantBookManager(this);

        eventRegister();
        commandRegister();
        reloadVariables();

        getServer().getConsoleSender().sendMessage(PREFIX + " 플러그인이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + " 플러그인이 비활성화 되었습니다.");
    }

    private void eventRegister() {
        getServer().getPluginManager().registerEvents(new LottoEnchantBookEvent(this), this);
    }

    private void commandRegister() {
        getServer().getPluginCommand("인챈트뽑기").setExecutor(new MainCommand(this));
        getServer().getPluginCommand("lottoenchantbook").setExecutor(new SubCommand(this));
    }

    public void reloadVariables() {
        reloadConfig();
        lottoEnchantBookManager.reloadVariables();
    }

    protected FileConfiguration config;

    private void createConfigFiles() {
        File configf = new File(getDataFolder(), "config.yml");
        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public LottoEnchantBookManager getLottoEnchantBookManager() {
        return lottoEnchantBookManager;
    }
}
