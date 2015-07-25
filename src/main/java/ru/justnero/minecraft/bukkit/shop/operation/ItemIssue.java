package ru.justnero.minecraft.bukkit.shop.operation;

import java.util.HashMap;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.justnero.minecraft.bukkit.shop.Bootstrap;
import ru.justnero.minecraft.bukkit.shop.Issue;
import static ru.justnero.minecraft.bukkit.shop.UtilLog.error;

/**
 *
 * @author JustNero
 */
public class ItemIssue implements IIssue {
    
    @Override
    public boolean give(Issue is) {
        try {
            String atr[] = is.atributes.split(";");
            int id      = Integer.parseInt(atr[0]);
            short data  = Short.parseShort(atr[1]);
            int amount  = Integer.parseInt(atr[2]);
            ItemStack iss = new ItemStack(Material.getMaterial(id),amount,data);
            Player player = Bootstrap.server.getPlayer(is.name);
            HashMap<Integer, ItemStack> nope = player.getInventory().addItem(iss);
            for(Entry<Integer, ItemStack> entry : nope.entrySet()) {
                player.getWorld().dropItemNaturally(player.getLocation(),entry.getValue());
            }
            return true;
        } catch(Exception ex) {
            error(ex);
            return false;
        }
    }
    
    
}
