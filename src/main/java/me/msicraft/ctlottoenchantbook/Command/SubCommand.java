package me.msicraft.ctlottoenchantbook.Command;

import me.msicraft.ctlottoenchantbook.CTLottoEnchantBook;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SubCommand implements CommandExecutor {

    private final CTLottoEnchantBook plugin;

    public SubCommand(CTLottoEnchantBook plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equals("lottoenchantbook")) {
            if (sender instanceof Player player) {
                if (player.isOp()) {
                    String var = args[0];
                    switch (var) {
                        case "reload" -> {
                            plugin.reloadVariables();
                            player.sendMessage(Component.text(CTLottoEnchantBook.PREFIX + ChatColor.GREEN + " 플러그인 구성이 업데이트 되었습니다"));
                        }
                    }
                }
            }
        }
        return false;
    }

}
