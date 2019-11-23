package de.piinguiin.guiapi.manager;

import de.piinguiin.guiapi.impl.IGui;
import de.piinguiin.guiapi.listener.InventoryClickGuiListener;
import de.piinguiin.guiapi.listener.InventoryCloseGuiListener;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of CorePlugin and was created at the 18.11.2019
 *
 * CorePlugin can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class GuiManager {

  @Getter
  private final Object2ObjectOpenHashMap<Inventory, IGui> guis;


  public GuiManager(final JavaPlugin plugin) {
    guis = new Object2ObjectOpenHashMap<>();
    final PluginManager pluginManager = plugin.getServer().getPluginManager();
    pluginManager.registerEvents(new InventoryClickGuiListener(this), plugin);
    pluginManager.registerEvents(new InventoryCloseGuiListener(this), plugin);
  }

  public boolean isCached(final Inventory inventory) {
    return guis.containsKey(inventory);
  }

  public IGui getGuiFromInventory(final Inventory inventory) {
    return guis.get(inventory);
  }

  public void addGui(final IGui gui) {
    guis.put(gui.getBukkitInventory(), gui);
  }

  public void removeGui(final IGui gui) {

    if (!isCached(gui.getBukkitInventory())) {
      return;
    }

    guis.remove(gui.getBukkitInventory(), gui);
  }

}
