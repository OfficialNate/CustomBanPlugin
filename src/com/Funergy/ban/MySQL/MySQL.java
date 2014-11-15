package com.Funergy.ban.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	private Connection connection;
	 
    public MySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("XXXXX" + "?user=XXXXX" + "&password=XXXXXX" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getNameFromUUID(String UUID){
    	try{
    		PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER_DATA WHERE UUID='" + UUID + "';");
    		ResultSet result = statement.executeQuery();
    		
    		 
            if (result.next()) {
                    return result.getString("PlayerName");
            } else {
                    return "NotFound";
            }
    	}catch(Exception e){
    		e.printStackTrace();
    		
    	}
    	return "ERROR";
    }
    
    public boolean ContainsName(String UUID){
    	try{
    		PreparedStatement statement = connection.prepareStatement("SELECT UUID FROM USER_DATA WHERE UUID='" + UUID + "';");
    		ResultSet result = statement.executeQuery();
    		
    		 
            if (result.next()) {
               if(result.getString("UUID")!= null){
            	   return true;
               }else{
            	   return false;
               }
            } else {
                    return false;
            }
    	}catch(Exception e){
    		e.printStackTrace();
    		
    	}
    	return false;
    }
    public void updateName(String UUID,String pname){
    	try{
    		PreparedStatement statement = connection.prepareStatement("UPDATE USER_DATA SET PlayerName='" + pname + "' WHERE UUID='" + UUID + "';");
    		statement.executeUpdate();
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		
    	}
    }
    public void addPlayerToDB(String pname,String UUID){
    	try {                                                         
	    	  PreparedStatement statement = connection.prepareStatement("INSERT INTO USER_DATA (UUID,PlayerName, rank,soulpoints)\nVALUES ('"+UUID+"','" + pname + "', 'None','10');");
	    	  statement.executeUpdate();
	      }
	      catch (SQLException e1) {
	        e1.printStackTrace();
	      }
    }
    public void PlayerToNewStructure(String UUID,String pname){
    	try{
    		PreparedStatement statement = connection.prepareStatement("UPDATE USER_DATA SET UUID='" + UUID + "' WHERE PlayerName='" + pname + "';");
    		statement.executeUpdate();
    		PreparedStatement statement2 = connection.prepareStatement("UPDATE USER_DATA SET soulpoints='" + getCoinsFromOldStructure(pname) + "' WHERE PlayerName='" + pname + "';");
    		statement2.executeUpdate();
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		
    	}
    }
    public int getCoinsFromNewStructure(String UUID){
    	try{
  		  PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM USER_DATA WHERE UUID='" + UUID + "';");
  	    ResultSet set = statement2.executeQuery();
  	    int coinsNow;
  	    if (set.next())
  	      coinsNow = set.getInt("soulpoints");
  	    else {
  	      coinsNow = 0;
  	    }
  	    set.close();

  	    return coinsNow;
  	}catch(Exception e){
  		e.printStackTrace();
  	}
  	return -1;
    }
    public boolean isNewStructure(String UUID){
    	try{
    		  PreparedStatement statement2 = connection.prepareStatement("SELECT UUID FROM USER_DATA WHERE UUID='" + UUID + "';");
    	    ResultSet set = statement2.executeQuery();
    	    if (set.next()){
    	    	set.close();
    	    	return true;
    	    } else {
    	    	set.close();
    	      return false;
    	      
    	    }
    	    
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
    public int getCoinsFromOldStructure(String pname){
    	try{
    		  PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM SoulPoints WHERE PlayerName='" + pname + "';");
    	    ResultSet set = statement2.executeQuery();
    	    int coinsNow;
    	    if (set.next())
    	      coinsNow = set.getInt("coins");
    	    else {
    	      coinsNow = 0;
    	    }
    	    set.close();

    	    return coinsNow;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return 0;
    	  }
    public String getUUIDFromName(String pname){
    	try{
    		PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER_DATA WHERE PlayerName='" + pname + "';");
    		ResultSet result = statement.executeQuery();
    		
    		 
            if (result.next()) {
                    return result.getString("UUID");
            } else {
                    return "None";
            }
    	}catch(Exception e){
    		e.printStackTrace();
    		
    	}
    	return "None";
    }
    
    public void permBan(String uuid,String name,String reason){
    	try{
    		PreparedStatement statement = connection.prepareStatement("INSERT INTO `BANS`(UUID, NAME, REASON,LENGTH,PERM)\nvalues ('" + uuid + "', '" + name + "','"+reason+"','"+"9999999999"+"','"+"TRUE"+"');");
    		statement.executeUpdate();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public void unBan(String uuid){
    	try{
    		PreparedStatement statement = connection.prepareStatement("DELETE FROM BANS where UUID='"+uuid+"';");
    		statement.executeUpdate();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public void tempBan(String uuid,String name,String reason,Long length){
    	try{
    		PreparedStatement statement = connection.prepareStatement("INSERT INTO `BANS`(UUID, NAME, REASON,LENGTH,PERM)\nvalues ('" + uuid + "', '" + name + "','"+reason+"','"+length+"','"+"FALSE"+"');");
    		statement.executeUpdate();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public boolean isPermBanned(String uuid){
    	try{
    		PreparedStatement statement = connection.prepareStatement("SELECT PERM FROM `BANS` WHERE UUID='"+uuid+"';");
    		ResultSet result = statement.executeQuery();
    		if(result.next()){
    			if(result.getString("PERM") != "TRUE"){
    				return false;
    			}else{
    				return true;
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public boolean isBanned(String uuid){
    	try{
    		PreparedStatement statement = connection.prepareStatement("SELECT UUID FROM `BANS` WHERE UUID='"+uuid+"';");
    		ResultSet result = statement.executeQuery();
    		if(result.next()){
    			if(result.getString("UUID") == null){
    				return false;
    			}else{
    				return true;
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
    public String getReason(String uuid){
    	try{
    		PreparedStatement statement = connection.prepareStatement("SELECT REASON FROM `BANS` WHERE UUID='"+uuid+"';");
    		ResultSet result = statement.executeQuery();
    		if(result.next()){
    			return result.getString("REASON");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    
    
    
    public void Mute(String uuid,String name,String reason){
    	try{
    		PreparedStatement statement = connection.prepareStatement("INSERT INTO `MUTES`(UUID, NAME, REASON,LENGTH)\nvalues ('" + uuid + "', '" + name + "','"+reason+"','"+"FOREVER"+"');");
    		statement.executeUpdate();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public void unMute(String uuid){
    	try{
    		PreparedStatement statement = connection.prepareStatement("DELETE FROM MUTES where UUID='"+uuid+"';");
    		statement.executeUpdate();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public void tempMute(String uuid,String name,String reason,String length){
    	try{
    		PreparedStatement statement = connection.prepareStatement("INSERT INTO `MUTES`(UUID, NAME, REASON,LENGTH)\nvalues ('" + uuid + "', '" + name + "','"+reason+"','"+length+"');");
    		statement.executeUpdate();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
    
    
    public boolean isMuted(String uuid){
    	try{
    		PreparedStatement statement = connection.prepareStatement("SELECT UUID FROM `MUTES` WHERE UUID='"+uuid+"';");
    		ResultSet result = statement.executeQuery();
    		if(result.next()){
    			if(result.getString("UUID") == null){
    				return false;
    			}else{
    				return true;
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public Long getTempBanLength(String UUID){
    	try{
    		PreparedStatement statement = connection.prepareStatement("SELECT LENGTH FROM `BANS` WHERE UUID='"+UUID+"';");
    		ResultSet result = statement.executeQuery();
    		if(result.next()){
    			return result.getLong("LENGTH");
    		}else{
    			return null;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    
    
    
    
    
    
    
    public String getRank(String UUID){
    	try{
    		PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER_DATA WHERE UUID='" + UUID + "';");
    		ResultSet result = statement.executeQuery();
    		
    		 
            if (result.next()) {
                    return result.getString("rank");
            } else {
                    return "None";
            }
    	}catch(Exception e){
    		e.printStackTrace();
    		
    	}
    	return "None";
    }
    public void disconect(){
    	try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   

}
