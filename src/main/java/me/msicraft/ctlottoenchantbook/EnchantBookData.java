package me.msicraft.ctlottoenchantbook;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantBookData {

    private final Enchantment enchantment;
    private final int level;
    private final int amount;
    private final int weight;

    public EnchantBookData(Enchantment enchantment, int level, int amount, int weight) {
        this.enchantment = enchantment;
        this.level = level;
        this.amount = amount;
        this.weight = weight;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }

    public int getAmount() {
        return amount;
    }

    public int getWeight() {
        return weight;
    }

    public ItemStack getEnchantBook() {
        if (enchantment == null) {
            return new ItemStack(Material.AIR);
        }
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemMeta;
        if (enchantmentStorageMeta != null) {
            enchantmentStorageMeta.addStoredEnchant(enchantment, level, true);
        } else {
            return new ItemStack(Material.AIR);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
