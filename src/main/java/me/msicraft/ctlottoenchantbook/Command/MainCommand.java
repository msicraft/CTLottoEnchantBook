package me.msicraft.ctlottoenchantbook.Command;

import me.msicraft.ctlottoenchantbook.CTLottoEnchantBook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    private final CTLottoEnchantBook plugin;

    public MainCommand(CTLottoEnchantBook plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equals("인챈트뽑기")) {
            if (sender instanceof Player player) {
                if (player.isOp()) {
                    player.getInventory().addItem(plugin.getLottoEnchantBookManager().getLottoEnchantBook());
                }
            }
        }
        return false;
    }

}
