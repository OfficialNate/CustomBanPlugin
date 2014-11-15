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

import com.Funergy.ban.MySQL.MySQL;

/**
 * @author Funergy
 *
 */
public class RankHandler {
	public static MySQL mysql;
	public static HashMap<String, String> ranks = new HashMap<String, String>();

	public static void putInHashMap(String UUID){
		mysql= new MySQL();
		String s = mysql.getRank(UUID);
		ranks.put(UUID, s);
		mysql.disconect();
	}
	public static String getRank(String UUID){
			return ranks.get(UUID);
	}
	public static void removePlayer(String UUID){
		if(ranks.containsKey(UUID)){
			ranks.remove(UUID);
		}
	}

}
