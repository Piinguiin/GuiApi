package de.piinguiin.guiapi.impl;

import de.piinguiin.guiapi.icon.impl.IIcon;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface IGui {

    String getTitle();

    Int2ObjectOpenHashMap<IIcon> getIcons();

    Inventory getBukkitInventory();

    void fill();

    void open(Player player);

    void close(Player player);

    void setIcon(int slot, IIcon icon, boolean update);

    void removeIcon(int slot, boolean update);

    void changeIcon(int slot, IIcon icon);

    void update();

    void handleClickEvent(InventoryClickEvent event);

}