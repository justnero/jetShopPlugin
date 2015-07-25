package ru.justnero.minecraft.bukkit.shop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.InvalidConfigurationException;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author JustNero
 */
public class Config {

    private static final File file = new File("plugins/JetShop/config.yml");
    private static final YamlConfiguration config = new YamlConfiguration();
    
    private static String language = "en";
    private static String driver = "sql";
    private static String sqlDSN = "sql";
    private static String sqlUser = "sql";
    private static String sqlPassword = "sql";
    private static int issuesPerPage = 10;
    private static int server = -1;
    
    public static void load() throws IOException, InvalidConfigurationException {
        fileCheck();
        config.load(file);
        
        language    = config.getString("language",getLanguage());
        driver      = config.getString("driver",getDriver());
        sqlDSN      = config.getString("sql.dsn",getSqlDSN());
        sqlUser     = config.getString("sql.user",getSqlUser());
        sqlPassword = config.getString("sql.password",getSqlPassword());
        issuesPerPage = config.getInt("issuesPerPage",getIssuesPerPage());
        server        = config.getInt("server",getServer());
    }

    public static String getLanguage() {
        return language;
    }

    public static String getDriver() {
        return driver;
    }

    public static String getSqlDSN() {
        return sqlDSN;
    }

    public static String getSqlUser() {
        return sqlUser;
    }

    public static String getSqlPassword() {
        return sqlPassword;
    }

    public static int getIssuesPerPage() {
        return issuesPerPage;
    }
    
    public static int getServer() {
        return server;
    }
    
    protected static void fileCheck() throws IOException {
        if(!file.exists()){
            InputStream resourceAsStream = Bootstrap.class.getResourceAsStream("/config.yml");
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buff = new byte[65536];
            int n;
            while((n = resourceAsStream.read(buff)) > 0){
                fos.write(buff,0,n);
                fos.flush();
            }
            fos.close();
            buff = null;
        }
    }
    
}
