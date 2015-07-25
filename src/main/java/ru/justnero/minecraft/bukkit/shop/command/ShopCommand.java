package ru.justnero.minecraft.bukkit.shop.command;

import java.util.List;
import org.bukkit.entity.Player;
import ru.justnero.minecraft.bukkit.shop.Bootstrap;
import ru.justnero.minecraft.bukkit.shop.Config;
import static ru.justnero.minecraft.bukkit.shop.Language.langGet;
import ru.justnero.minecraft.bukkit.shop.Issue;

/**
 *
 * @author JustNero
 */
public class ShopCommand extends BasicCommand {
    
    static {
        requiredPermissions = new String[]{"user"};
    }
    
    @Override
    public boolean execute(Player player, String[] args) {
        switch(args.length) {
            case 1: 
                if(args[0].equalsIgnoreCase("list")) { // @CMD /shop list
                    printPage(player,0);
                } else {
                    player.sendMessage(langGet("illegalArguments"));
                    player.sendMessage(langGet("commandSyntax"));
                }
                break;
            case 2: 
                if(args[0].equalsIgnoreCase("list")) { // @CMD /shop list {page}
                    try {
                        printPage(player,(Integer.valueOf(args[1])-1)*Config.getIssuesPerPage());
                    } catch(NumberFormatException ex) { // Goes here if there is not a number
                        player.sendMessage(langGet("illegalArguments"));
                        player.sendMessage(langGet("commandSyntax"));
                    }
                    
                } else if(args[0].equalsIgnoreCase("get")) {
                    if(args[1].equalsIgnoreCase("all")) { // @CMD /shop get all
                        List<Issue> list = Bootstrap.driver.get(player.getName());
                        for(Issue is : list) {
                            Bootstrap.execute(is);
                        }
                    } else { // @CMD /shop get {id}
                        try {
                            int id = Integer.valueOf(args[1]);
                            Issue is = Bootstrap.driver.get(id,player.getName());
                            Bootstrap.execute(is);
                        } catch(NumberFormatException ex) { // Goes here if there id not a number
                            player.sendMessage(langGet("illegalArguments"));
                            player.sendMessage(langGet("commandSyntax"));
                        }
                    }
                } else {
                    player.sendMessage(langGet("illegalArguments"));
                        player.sendMessage(langGet("commandSyntax"));
                }
                
                
                
                break;
            default:
                player.sendMessage(langGet("illegalArguments"));
                player.sendMessage(langGet("commandSyntax"));
                break;
        }
        return true;
    }
    
    private void printPage(Player player, int offset) {
        List<Issue> list = Bootstrap.driver.get(player.getName());
        Issue is;
        int page = Config.getIssuesPerPage();
        if(list.size() > offset) {
            player.sendMessage(langGet("pageTitle",((int) (offset/page)) + 1));
            for(int i = offset;(i<offset+page) && (i<list.size());i++) {
                is = list.get(i);
                player.sendMessage(langGet("pageElement",is.id,is.toString()));
            }
        } else {
            player.sendMessage(langGet("pageEmpty"));
        }
    }
    
}
