package in.sqrock.railway.dao;

import in.sqrock.railway.config.Database;
import in.sqrock.railway.model.*;
import java.sql.*;
import java.util.*;

public class BookingDao {
    public int book(int userId, Train train, String passenger, int age, String gender, String date) throws Exception {
        try(Connection c=Database.getConnection()){
            c.setAutoCommit(false);
            try {
                int available;
                try(PreparedStatement p=c.prepareStatement("SELECT available_seats FROM trains WHERE id=? FOR UPDATE")){
                    p.setInt(1,train.getId()); try(ResultSet r=p.executeQuery()){if(!r.next()) throw new SQLException("Train not found."); available=r.getInt(1);}
                }
                if(available<=0) throw new SQLException("Sorry, no seats are available.");
                int seat;
                try(PreparedStatement p=c.prepareStatement("SELECT COALESCE(MAX(seat_number),0)+1 FROM bookings WHERE train_id=? AND journey_date=? AND status='CONFIRMED'")){
                    p.setInt(1,train.getId()); p.setString(2,date); try(ResultSet r=p.executeQuery()){r.next(); seat=r.getInt(1);}
                }
                try(PreparedStatement p=c.prepareStatement("INSERT INTO bookings(user_id,train_id,passenger_name,age,gender,journey_date,seat_number,status) VALUES(?,?,?,?,?,?,?,'CONFIRMED')")){
                    p.setInt(1,userId);p.setInt(2,train.getId());p.setString(3,passenger);p.setInt(4,age);p.setString(5,gender);p.setString(6,date);p.setInt(7,seat);p.executeUpdate();
                }
                try(PreparedStatement p=c.prepareStatement("UPDATE trains SET available_seats=available_seats-1 WHERE id=?")){p.setInt(1,train.getId());p.executeUpdate();}
                c.commit(); return seat;
            } catch(Exception e){c.rollback();throw e;} finally{c.setAutoCommit(true);}
        }
    }
    public List<Booking> history(int userId) throws Exception {
        List<Booking> list=new ArrayList<>();
        String sql="SELECT b.id,b.passenger_name,b.seat_number,b.journey_date,b.status,t.train_number,t.train_name,t.source_station,t.destination_station FROM bookings b JOIN trains t ON b.train_id=t.id WHERE b.user_id=? ORDER BY b.created_at DESC";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,userId);try(ResultSet r=p.executeQuery()){while(r.next())list.add(new Booking(r.getInt("id"),r.getInt("train_number"),r.getInt("seat_number"),r.getString("passenger_name"),r.getString("train_name"),r.getString("source_station"),r.getString("destination_station"),r.getString("journey_date"),r.getString("status")));}}
        return list;
    }
    public void cancel(int bookingId,int userId) throws Exception {
        try(Connection c=Database.getConnection()){
            c.setAutoCommit(false);
            try{
                int trainId;
                try(PreparedStatement p=c.prepareStatement("SELECT train_id FROM bookings WHERE id=? AND user_id=? AND status='CONFIRMED' FOR UPDATE")){p.setInt(1,bookingId);p.setInt(2,userId);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("Confirmed booking not found.");trainId=r.getInt(1);}}
                try(PreparedStatement p=c.prepareStatement("UPDATE bookings SET status='CANCELLED' WHERE id=?")){p.setInt(1,bookingId);p.executeUpdate();}
                try(PreparedStatement p=c.prepareStatement("UPDATE trains SET available_seats=available_seats+1 WHERE id=?")){p.setInt(1,trainId);p.executeUpdate();}
                c.commit();
            }catch(Exception e){c.rollback();throw e;}finally{c.setAutoCommit(true);}
        }
    }
}
