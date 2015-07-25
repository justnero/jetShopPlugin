package ru.justnero.minecraft.bukkit.shop;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import static ru.justnero.minecraft.bukkit.mprotect.UtilLog.error;
import static ru.justnero.minecraft.bukkit.mprotect.UtilLog.info;
import ru.justnero.minecraft.bukkit.shop.command.ShopCommand;

import ru.justnero.minecraft.bukkit.shop.driver.IDriver;
import ru.justnero.minecraft.bukkit.shop.driver.SqlDriver;
import ru.justnero.minecraft.bukkit.shop.operation.GroupIssue;
import ru.justnero.minecraft.bukkit.shop.operation.IIssue;
import ru.justnero.minecraft.bukkit.shop.operation.ItemIssue;
import ru.justnero.minecraft.bukkit.shop.operation.RegionIssue;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author JustNero
 */
public class Bootstrap extends JavaPlugin {
    
    public static Server        server;
    public static JavaPlugin    instance;
    public static IDriver       driver;
    
    public static ru.justnero.minecraft.bukkit.mprotect.Bootstrap    MP;
    
    private static final IIssue itemIs  = new ItemIssue();
    private static final IIssue groupIs = new GroupIssue();
    private static final IIssue regionIs = new RegionIssue();
    
    @Override
    public void onEnable(){
        instance = this;
        server = getServer();
        try {
            Config.load();
            Language.langLoad(Config.getLanguage());
            driver = new SqlDriver();
        } catch(Exception ex) {
            error("Error loading configuration");
            error(ex);
            server.getPluginManager().disablePlugin(this);
            return;
        }
        if(!registerMP()) {
            server.getPluginManager().disablePlugin(this);
            return;
        }
        getCommand("shop").setExecutor(new ShopCommand());
        info("Everything is ready, capitan. Engage!");
    }
    
    @Override
    public void onDisable(){
        info("I am disabling, see you next time");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
    
    public static void execute(Issue is) {
        boolean result = false;
        switch(is.type) {
            case ITEM:
                result = itemIs.give(is);
                break;
            case GROUP:
                result = groupIs.give(is);
                break;
            case REGION:
                result = regionIs.give(is);
                break;
        }
        if(result) {
            driver.mark(is);
        }
    }
    
    private boolean registerMP() {
        Plugin mProtect = server.getPluginManager().getPlugin("mProtect");
        if(mProtect == null) {
            error("mProtect does not appear to be installed.");
        } else if(mProtect instanceof ru.justnero.minecraft.bukkit.mprotect.Bootstrap) {
            MP = (ru.justnero.minecraft.bukkit.mprotect.Bootstrap) mProtect;
            info("mProtect loaded.");
            return true;
        } else {
            error("mProtect detection failed (report error).");
        }
        return false;
    }

    public static boolean hasPerm(CommandSender sender, String perm){
        return PermissionsEx.getUser((Player)sender).has("jetshop."+perm);
        
    }
    
}
