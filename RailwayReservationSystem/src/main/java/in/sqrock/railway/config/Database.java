package in.sqrock.railway.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class Database {
    private static final Properties PROPERTIES = new Properties();
    static {
        try (InputStream in = new FileInputStream("db.properties")) {
            PROPERTIES.load(in);
        } catch (Exception e) {
            PROPERTIES.setProperty("db.url", "jdbc:mysql://localhost:3306/railway_reservation?useSSL=false&serverTimezone=UTC");
            PROPERTIES.setProperty("db.username", "root");
            PROPERTIES.setProperty("db.password", "");
        }
    }
    private Database() { }
    public static Connection getConnection() throws Exception {
        String url = PROPERTIES.getProperty("db.url");
        String user = PROPERTIES.getProperty("db.username", "root");
        String password = PROPERTIES.getProperty("db.password", "");
        return DriverManager.getConnection(url, user, password);
    }
    public static boolean testConnection() {
        try (Connection ignored = getConnection()) { return true; }
        catch (Exception e) { return false; }
    }
}
