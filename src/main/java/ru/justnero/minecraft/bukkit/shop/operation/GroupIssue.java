package ru.justnero.minecraft.bukkit.shop.operation;

import ru.justnero.minecraft.bukkit.shop.Issue;
import static ru.justnero.minecraft.bukkit.shop.UtilLog.error;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author JustNero
 */
public class GroupIssue implements IIssue {

    @Override
    public boolean give(Issue is) {
        try {
            String atr[] = is.atributes.split(";");
            String groupName = atr[0];
            long time = Long.parseLong(atr[1]);
            PermissionsEx.getUser(is.name).addGroup(groupName,null,time);
            return true;
        } catch(Exception ex) {
            error(ex);
            return false;
        }
    }

}
