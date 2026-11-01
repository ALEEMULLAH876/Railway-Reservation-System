package in.sqrock.railway.service;

import in.sqrock.railway.config.Database;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;

public class AuthService {
    private String hash(String value) throws Exception {
        byte[] bytes=MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder out=new StringBuilder(); for(byte b:bytes) out.append(String.format("%02x",b)); return out.toString();
    }
    public boolean register(String username, String password, String fullName) throws Exception {
        String sql="INSERT INTO users(username,password_hash,full_name) VALUES(?,?,?)";
        try(Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,username); p.setString(2,hash(password)); p.setString(3,fullName); p.executeUpdate(); return true;
        }
    }
    public int login(String username, String password) throws Exception {
        try(Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement("SELECT id,password_hash FROM users WHERE username=?")){
            p.setString(1,username); try(ResultSet r=p.executeQuery()){
                if(r.next() && MessageDigest.isEqual(r.getString("password_hash").getBytes(StandardCharsets.UTF_8), hash(password).getBytes(StandardCharsets.UTF_8))) return r.getInt("id");
            }
        } return -1;
    }
}
