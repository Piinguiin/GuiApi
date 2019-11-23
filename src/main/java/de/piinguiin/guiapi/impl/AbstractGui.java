package de.piinguiin.guiapi.impl;

import de.piinguiin.guiapi.PublicGui;
import de.piinguiin.guiapi.icon.impl.Clickable;
import de.piinguiin.guiapi.icon.impl.IIcon;
import de.piinguiin.guiapi.manager.GuiManager;
import de.piinguiin.guiapi.utils.ItemBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractGui implements IGui {

  private final GuiManager guiManager;
  @Getter
  private final String title;
  @Getter
  private final ItemStack placeHolder;
  @Getter
  private Inventory bukkitInventory;
  @Getter
  private final Int2ObjectOpenHashMap<IIcon> icons;
  @Getter
  private final boolean filled;

  @Override
  public void handleClickEvent(final InventoryClickEvent event) {
    final IIcon icon = icons.get(event.getSlot());
    if (icon == null || !(icon instanceof Clickable)) {
      return;
    }

    final Clickable clickable = ((Clickable) icon);

    if (event.getClick() == ClickType.RIGHT) {
      clickable.handleRightClick(event);
    }

    if (event.getClick() == ClickType.LEFT) {
      clickable.handleLeftClick(event);
    }

  }

  public AbstractGui(final GuiManager guiManager, final String title, final InventoryType type,
      final int raws, final boolean filled) {
    this.title = title;
    placeHolder = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
    icons = new Int2ObjectOpenHashMap<>();
    this.filled = filled;
    buildBukkitInventory(type, raws);
    update();
    this.guiManager = guiManager;
    guiManager.addGui(this);
  }

  public AbstractGui(final GuiManager guiManager, final String title, final InventoryType type,
      final boolean filled) {
    this(guiManager, title, type, 0, filled);
  }

  public AbstractGui(
      final GuiManager guiManager, final String title, final int raws, final boolean filled) {
    this(guiManager, title, InventoryType.CHEST, raws, filled);
  }

  private void buildBukkitInventory(final InventoryType type, final int raws) {
    final Inventory inventory;

    if (!type.equals(InventoryType.CHEST)) {
      inventory = Bukkit.createInventory(null, type, title);
    } else {
      inventory = Bukkit.createInventory(null, 9 * raws, title);
    }

    bukkitInventory = inventory;
  }

  public void closeAndRemove(final Player player) {

    if(!(this instanceof PublicGui)) return;

    guiManager.removeGui(this);
    close(player);
  }

  @Override
  public void removeIcon(final int slot, final boolean update) {

    icons.remove(slot);

    if (update) {
      bukkitInventory.setItem(slot, placeHolder);
    }

  }

  @Override
  public void setIcon(final int slot, final IIcon icon, final boolean update) {

    icons.put(slot, icon);

    if (update) {
      bukkitInventory.setItem(slot, icon.getDisplayItem());
    }

  }

  @Override
  public void changeIcon(final int slot, final IIcon icon) {
    removeIcon(slot, false);
    setIcon(slot, icon, true);
  }

  @Override
  public void update() {

    bukkitInventory.clear();

    if (filled) {
      fill();
    }

    for (final Map.Entry<Integer, IIcon> entry : icons.entrySet()) {
      bukkitInventory.setItem(entry.getKey(), entry.getValue().getDisplayItem());
    }

  }

  @Override
  public void fill() {

    for (int i = 0; i < bukkitInventory.getSize(); i++) {
      bukkitInventory.setItem(i, placeHolder);
    }

  }

  @Override
  public void open(final Player player) {
    player.openInventory(getBukkitInventory());
  }
}