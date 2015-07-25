package ru.justnero.minecraft.bukkit.shop.operation;

import ru.justnero.minecraft.bukkit.mprotect.User;
import ru.justnero.minecraft.bukkit.mprotect.protect.Protect;
import ru.justnero.minecraft.bukkit.mprotect.protect.ProtectConfig;
import ru.justnero.minecraft.bukkit.shop.Bootstrap;
import ru.justnero.minecraft.bukkit.shop.Issue;
import static ru.justnero.minecraft.bukkit.shop.UtilLog.error;

/**
 *
 * @author JustNero
 */
public class RegionIssue implements IIssue {
    
    @Override
    public boolean give(Issue is) {
        try {
            String atr[] = is.atributes.split(";");
            String protectName = atr[0];
            String configName = atr[1];
            User user = Bootstrap.MP.db.get(is.name);
            user.protects.put(protectName,new Protect(protectName,ProtectConfig.list.get(configName)));
            Bootstrap.MP.db.set(user);
            return true;
        } catch(Exception ex) {
            error(ex);
            return false;
        }
    }
    
    
}
