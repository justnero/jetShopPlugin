package ru.justnero.minecraft.bukkit.shop.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ru.justnero.minecraft.bukkit.shop.Config;
import ru.justnero.minecraft.bukkit.shop.Issue;
import static ru.justnero.minecraft.bukkit.shop.UtilLog.error;

/**
 *
 * @author JustNero
 */
public class SqlDriver implements IDriver {
    
    private static final String tableIssue = "shop_issue";
    private static final String tableUsers = "user";
    
    @Override
    public List<Issue> get(String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connect = null;
        List<Issue> list = new ArrayList<Issue>();
        Issue is = null;
        try {
            connect = getSqlConnection();
            ps = connect.prepareStatement("SELECT i.id as id,u.name as name,i.type as type,i.atributes as atributes FROM `"+ tableIssue +"` i,`"+tableUsers+"` u WHERE i.sid = ? AND u.name = ? AND i.uid = u.id AND i.status = 'pending'");
            ps.setInt(1,Config.getServer());
            ps.setString(2,name);
            
            rs = ps.executeQuery();
            while(rs.next()) {
                Issue.Type type;
                String typeStr = rs.getString("type");
                if(typeStr.equalsIgnoreCase("item")) {
                    type = Issue.Type.ITEM;
                } else if(typeStr.equalsIgnoreCase("group")) {
                    type = Issue.Type.GROUP;
                } else if(typeStr.equalsIgnoreCase("region")) {
                    type = Issue.Type.REGION;
                } else {
                    continue;
                }
                is = new Issue(rs.getInt("id"),rs.getString("name"),type,rs.getString("atributes"));
                list.add(is);
            }
        } catch(Exception ex) {
            error("Error getting issues");
            error(ex);
        } finally { 
            try {
                if(ps != null)
                    ps.close();
                if (connect != null)
                    connect.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                error("Failed to close SQL connection");
                error(ex);
            }
        }
        return list;
    }

    @Override
    public Issue get(int id, String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connect = null;
        Issue is = null;
        try {
            connect = getSqlConnection();
            ps = connect.prepareStatement("SELECT i.id as id,u.name as name,i.type as type,i.atributes as atributes FROM `"+ tableIssue +"` i,`"+tableUsers+"` u WHERE i.sid = ? AND i.id = ? AND u.name = ? AND i.uid = u.id AND i.status = 'pending'");
            ps.setInt(1,Config.getServer());
            ps.setInt(2,id);
            ps.setString(3,name);
            
            rs = ps.executeQuery();
            if(rs.next()) {
                Issue.Type type;
                String typeStr = rs.getString("type");
                if(typeStr.equalsIgnoreCase("item")) {
                    type = Issue.Type.ITEM;
                } else if(typeStr.equalsIgnoreCase("group")) {
                    type = Issue.Type.GROUP;
                } else if(typeStr.equalsIgnoreCase("region")) {
                    type = Issue.Type.REGION;
                } else {
                    return is;
                }
                is = new Issue(rs.getInt("id"),rs.getString("name"),type,rs.getString("atributes"));
            }
        } catch(Exception ex) {
            error("Error getting issue");
            error(ex);
        } finally { 
            try {
                if(ps != null)
                    ps.close();
                if (connect != null)
                    connect.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) {
                error("Failed to close SQL connection");
                error(ex);
            }
        }
        return is;
    }

    @Override
    public void mark(Issue is) {
        PreparedStatement ps = null;
        Connection connect = null;
        try {
            connect = getSqlConnection();
            ps = connect.prepareStatement("UPDATE `"+ tableIssue +"` i SET i.status = 'processed',processdate = ? WHERE i.id = ?");
            ps.setLong(1,System.currentTimeMillis()/1000L);
            ps.setInt(2,is.id);
            
            ps.executeUpdate();
        } catch(Exception ex) {
            error("Error getting issue");
            error(ex);
        } finally { 
            try {
                if(ps != null)
                    ps.close();
                if (connect != null)
                    connect.close();
            } catch (SQLException ex) {
                error("Failed to close SQL connection");
                error(ex);
            }
        }
    }
    
    public Connection getSqlConnection() {
        
        String sqlDSN       = Config.getSqlDSN();
        String sqlUser      = Config.getSqlUser();
        String sqlPassword  = Config.getSqlPassword();
        try {
            return DriverManager.getConnection(sqlDSN + "?autoReconnect=true",sqlUser,sqlPassword);
        } catch (SQLException ex) {
            throw new RuntimeException("Can`t connect to database",ex);
        }
    }
    
}
