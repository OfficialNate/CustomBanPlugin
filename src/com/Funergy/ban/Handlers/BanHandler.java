/*******************************************************************
 * Copyright (c) 2014 Soulpoint Company
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS 
 * ECLIPSE PUBLIC LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR 
 * DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT’S ACCEPTANCE
 * OF THIS AGREEMENT. The full license is available at:
 * http://www.eclipse.org/org/documents/epl-v10.php
 ******************************************************************/
package com.Funergy.ban.Handlers;

import java.util.HashMap;

/**
 * @author Funergy
 *
 */
public class BanHandler {
	public static HashMap<String,Boolean> perms = new HashMap<String,Boolean>();
	public static HashMap<String,String> reason = new HashMap<String,String>();
	public static HashMap<String,Long> tempBanTime = new HashMap<String,Long>();

	
	public static void addPlayer(boolean perm,String UUID,Long l,String r){
		if(perm){
			perms.put(UUID, perm);
		}else{
			perms.put(UUID, perm);
			tempBanTime.put(UUID, l);
		}
		reason.put(UUID, r);
	}
	public static boolean isPerm(String UUID){
		if(perms.containsKey(UUID)){
			return perms.get(UUID);
		}
		return false;
	}
	public static void removePlayer(String UUID){
		if(perms.containsKey(UUID)){
			perms.remove(UUID);
		}
		if(reason.containsKey(UUID)){
			reason.remove(UUID);
		}
		if(tempBanTime.containsKey(UUID)){
			tempBanTime.remove(UUID);
		}
	}
	public static String getReason(String UUID){
		if(reason.containsKey(UUID)){
			return reason.get(UUID);
		}
		return "[ERROR] Please contact a System Administrator!";
	}
	public static Long getTime(String UUID){
		if(tempBanTime.containsKey(UUID)){
			return tempBanTime.get(UUID);
		}
		return null;
	}
	public static boolean isBanned(String UUID){
		if(perms.containsKey(UUID)){
			return true;
		}
		return false;
	}

}
