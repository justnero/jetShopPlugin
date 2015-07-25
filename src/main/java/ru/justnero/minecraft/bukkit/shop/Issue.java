package ru.justnero.minecraft.bukkit.shop;

/**
 *
 * @author JustNero
 */
public class Issue {
    
    public final int    id;
    public final String name;
    public final Type   type;
    public final String atributes;
    
    public Issue(int id, String name, Type type, String atributes) {
        this.id         = id;
        this.name       = name;
        this.type       = type;
        this.atributes  = atributes;
    }
    public static enum Type {
        ITEM,GROUP,REGION,
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(" for ");
        sb.append(name);
        sb.append(": ");
        sb.append(type.toString());
        sb.append(".");
        sb.append(atributes);
        return sb.toString();
    }
    
}
