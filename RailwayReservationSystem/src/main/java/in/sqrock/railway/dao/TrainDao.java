package in.sqrock.railway.dao;

import in.sqrock.railway.config.Database;
import in.sqrock.railway.model.Train;
import java.sql.*;
import java.util.*;

public class TrainDao {
    public List<Train> search(String source, String destination) throws Exception {
        List<Train> list=new ArrayList<>();
        String sql="SELECT * FROM trains WHERE LOWER(source_station) LIKE ? AND LOWER(destination_station) LIKE ? ORDER BY departure_time";
        try(Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,"%"+source.toLowerCase()+"%"); p.setString(2,"%"+destination.toLowerCase()+"%");
            try(ResultSet r=p.executeQuery()){while(r.next()) list.add(new Train(r.getInt("id"),r.getInt("train_number"),r.getString("train_name"),r.getString("source_station"),r.getString("destination_station"),r.getString("departure_time"),r.getString("arrival_time"),r.getInt("available_seats")));}
        } return list;
    }
}
