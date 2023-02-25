package cores.api

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

class ItemBuilder {
    private var itemStack: ItemStack
    private var itemMeta: ItemMeta
    private val skullMeta: SkullMeta? = null

    constructor(itemStack: ItemStack) {
        this.itemStack = itemStack
        itemMeta = itemStack.itemMeta
    }

    constructor(material: Material?) {
        itemStack = ItemStack(material!!)
        itemMeta = itemStack.itemMeta
    }

    fun setDisplayName(name: String?): ItemBuilder {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name!!))
        return this
    }

    fun setMetaID(metaID: Byte): ItemBuilder {
        itemStack.data!!.data = metaID
        return this
    }

    fun setAmount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }

    fun setDurability(durability: Short): ItemBuilder {
        itemStack.durability = durability
        return this
    }

    fun addEnchantment(enchantment: Enchantment?, lvl: Int): ItemBuilder {
        itemMeta.addEnchant(enchantment!!, lvl, false)
        return this
    }

    fun clearEnchantments(): ItemBuilder {
        itemMeta.enchants.forEach { (enchantment: Enchantment?, integer: Int?) -> }
        return this
    }

    fun removeEnchantment(enchantment: Enchantment?): ItemBuilder {
        if (itemMeta.enchants.containsKey(enchantment)) itemMeta.removeEnchant(enchantment!!)
        return this
    }

    fun setLore(lines: List<String?>?): ItemBuilder {
        itemMeta.lore = lines
        return this
    }

    fun setLore(vararg lines: String?): ItemBuilder {
        itemMeta.lore = Arrays.asList(*lines)
        return this
    }

    fun resetLore(): ItemBuilder {
        itemMeta.lore!!.clear()
        return this
    }

    fun build(): ItemStack {
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}