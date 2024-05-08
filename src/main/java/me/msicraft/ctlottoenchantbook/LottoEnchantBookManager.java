package me.msicraft.ctlottoenchantbook;

import me.msicraft.ctcore.aCommon.Pair;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.PatternSyntaxException;

public class LottoEnchantBookManager {

    private final CTLottoEnchantBook plugin;

    public LottoEnchantBookManager(CTLottoEnchantBook plugin) {
        this.plugin = plugin;
    }

    private int totalCount = 1;

    private final List<Pair<EnchantBookData, Double>> enchantBookWeightList = new ArrayList<>();

    public void reloadVariables() {
        this.totalCount = plugin.getConfig().contains("Settings.TotalCount") ? plugin.getConfig().getInt("Settings.TotalCount") : 1;

        enchantBookWeightList.clear();

        double totalWeight = 0;
        List<String> formatList = plugin.getConfig().getStringList("LottoEnchantBook");
        List<EnchantBookData> tempList = new ArrayList<>();
        for (String format : formatList) {
            try {
                String[] a = format.split(":");
                String enchantNameKey = a[0].toLowerCase();
                int level = Integer.parseInt(a[1]);
                int amount = Integer.parseInt(a[2]);
                int weight = Integer.parseInt(a[3]);
                if (enchantNameKey.equals("air")) {
                    EnchantBookData enchantBookData = new EnchantBookData(null, level, amount, weight);
                    tempList.add(enchantBookData);
                    totalWeight = totalWeight + weight;
                    continue;
                }
                Enchantment enchantment = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(enchantNameKey));
                if (enchantment != null) {
                    EnchantBookData enchantBookData = new EnchantBookData(enchantment, level, amount, weight);
                    tempList.add(enchantBookData);
                    totalWeight = totalWeight + weight;
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Invalid LottoEnchantBook format: " + format);
                }
            } catch (PatternSyntaxException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Invalid LottoEnchantBook format: " + format);
            }
        }
        for (EnchantBookData enchantBookData : tempList) {
            double value = enchantBookData.getWeight() / totalWeight;
            Pair<EnchantBookData, Double> pair = new Pair<>(enchantBookData, value);
            enchantBookWeightList.add(pair);
        }
        enchantBookWeightList.sort(Comparator.comparingDouble(Pair::getV2));
    }

    public EnchantBookData getRandomEnchantBookData() {
        double pivot = Math.random();
        double weight = 0;
        for (Pair<EnchantBookData, Double> pair : enchantBookWeightList) {
            weight = weight + pair.getV2();
            if (pivot <= weight) {
                return pair.getV1();
            }
        }
        Bukkit.getConsoleSender().sendMessage(Component.text(CTLottoEnchantBook.PREFIX + ChatColor.RED + " 랜덤 인챈트 뽑기 오류 발생"));
        return null;
    }

    public ItemStack getLottoEnchantBook() {
        ItemStack itemStack = new ItemStack(Material.BOOK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String s = plugin.getConfig().contains("Book.DisplayName") ? plugin.getConfig().getString("Book.DisplayName") : "인챈트 뽑기";
        itemMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', s)));
        itemMeta.setCustomModelData(plugin.getConfig().contains("Book.CustomModelData") ? plugin.getConfig().getInt("Book.CustomModelData") : -1);
        List<Component> lore = new ArrayList<>();
        List<String> loreString = plugin.getConfig().getStringList("Book.Lore");
        for (String a : loreString) {
            a = ChatColor.translateAlternateColorCodes('&', a);
            lore.add(Component.text(a));
        }
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.set(new NamespacedKey(plugin, "CT_LottoEnchantBook"), PersistentDataType.STRING, "ct_lottoenchantbook");
        dataContainer.set(new NamespacedKey(plugin, "CT_LottoEnchantBook_Unstack"), PersistentDataType.STRING, UUID.randomUUID().toString());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
