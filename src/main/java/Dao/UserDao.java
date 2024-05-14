package Dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import Data.DatabaseConnectionManager;
import Model.User;

public class UserDao {
     public static boolean addUser(User user) {
    	 if (user == null || user.getEmail() == null) {
    	        return false;
    	    }
    	 try (Connection connection = DatabaseConnectionManager.getConnection()) {
 			PreparedStatement statement = connection.prepareStatement("insert into USER(username, email, password) values(?,?,?)");
 			String pass = encryptPassword(user.getPassword());
    		 statement.setString(1, user.getUsername());
    		 statement.setString(2, user.getEmail());
    		 statement.setString(3, pass);    		 
    		 int i = statement.executeUpdate();
    		 
    		 return i > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	 
     }
     
     public static String encryptPassword(String password) throws NoSuchAlgorithmException {
    	   if (password == null) {
    	        return null; 
    	    }
    	 MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	 digest.update(password.getBytes());
    	 byte[] bt = digest.digest();
    	 StringBuilder sd = new StringBuilder();
    	 for (byte b : bt) {
    		 sd.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
    	 }
		return sd.substring(0);
		
	}
     
     public static Boolean authenticate(User user) {
    	 try (Connection connection = DatabaseConnectionManager.getConnection()) {
  			PreparedStatement statement = connection.prepareStatement("SELECT *FROM USER WHERE EMAIL = ? AND PASSWORD = ?");

			String encryptPass = encryptPassword(user.getPassword());
			 statement.setString(1, user.getEmail());
    		 statement.setString(2, encryptPass);
    		 ResultSet resultset =  statement.executeQuery();
    		 
    		 return resultset.next();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	 
     }
     
     public static List<User> getAllUser() {
    	 List<User> users = new ArrayList<User>();
    	 try(Connection connection = DatabaseConnectionManager.getConnection()) {
  			PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	User user = new User();
            	user.setId(resultSet.getInt("id"));
            	user.setUsername(resultSet.getString("username"));
            	user.setEmail(resultSet.getString("email"));
            	user.setPassword(resultSet.getString("password"));
            	users.add(user);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 return users;
     }
}
