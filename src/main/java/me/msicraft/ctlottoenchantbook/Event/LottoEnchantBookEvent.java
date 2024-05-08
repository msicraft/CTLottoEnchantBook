package me.msicraft.ctlottoenchantbook.Event;

import me.msicraft.ctcore.Utils.EntityUtil;
import me.msicraft.ctlottoenchantbook.CTLottoEnchantBook;
import me.msicraft.ctlottoenchantbook.EnchantBookData;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class LottoEnchantBookEvent implements Listener {

    private final CTLottoEnchantBook plugin;

    public LottoEnchantBookEvent(CTLottoEnchantBook plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractLottoEnchantBook(PlayerInteractEvent e) {
        Action action = e.getAction();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            if (e.getHand() == EquipmentSlot.HAND) {
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (itemMeta != null) {
                        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
                        if (dataContainer.has(new NamespacedKey(plugin, "CT_LottoEnchantBook"), PersistentDataType.STRING)) {
                            String data = dataContainer.get(new NamespacedKey(plugin, "CT_LottoEnchantBook"), PersistentDataType.STRING);
                            if (data != null && data.equals("ct_lottoenchantbook")) {
                                EnchantBookData enchantBookData = plugin.getLottoEnchantBookManager().getRandomEnchantBookData();
                                if (enchantBookData != null) {
                                    itemStack.setAmount(itemStack.getAmount() - 1);
                                    if (enchantBookData.getEnchantment() == null) {
                                        player.sendMessage(Component.text(ChatColor.RED + "꽝"));
                                        return;
                                    }
                                    int amount = enchantBookData.getAmount();
                                    ItemStack enchantBook = enchantBookData.getEnchantBook();
                                    for (int a = 0; a < amount; a++) {
                                        int emptySlot = EntityUtil.getPlayerEmptySlot(player);
                                        if (emptySlot != -1) {
                                            player.getInventory().setItem(emptySlot, enchantBook);
                                        } else {
                                            player.getWorld().dropItemNaturally(player.getLocation(), enchantBook);
                                        }
                                    }
                                } else {
                                    player.sendMessage(Component.text(ChatColor.RED + "알 수 없는 오류가 발생했습니다."));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
