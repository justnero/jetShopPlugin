package ru.justnero.minecraft.bukkit.shop.driver;

import java.util.List;
import ru.justnero.minecraft.bukkit.shop.Issue;

/**
 *
 * @author JustNero
 */
public interface IDriver {
    
    /**
     * Returns all the issues we need to proceede for specific user
     * 
     * @param name user name
     * @return List of Issues
     */
    public List<Issue> get(String name);
    
    /**
     * Returns issue for specific ID and user
     * 
     * @param id of the issue
     * @param name user name
     * @return Issue if exists of null
     */
    public Issue get(int id, String name);
    
    
    /**
     * Marks specific issue as executed
     *
     * @param is issue to be marked as executed
     */
    public void mark(Issue is);
    
}
