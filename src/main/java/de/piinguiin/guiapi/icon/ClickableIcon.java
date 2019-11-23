package de.piinguiin.guiapi.icon;

import de.piinguiin.guiapi.icon.impl.Clickable;
import org.bukkit.inventory.ItemStack;

public abstract class ClickableIcon extends SimpleIcon implements Clickable {

    public ClickableIcon(ItemStack displayItem){
        super(displayItem);
    }

}