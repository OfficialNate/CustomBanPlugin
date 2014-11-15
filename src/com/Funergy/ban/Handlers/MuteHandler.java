/*******************************************************************
 * Copyright (c) 2014 Soulpoint Company
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS 
 * ECLIPSE PUBLIC LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR 
 * DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT’S ACCEPTANCE
 * OF THIS AGREEMENT. The full license is available at:
 * http://www.eclipse.org/org/documents/epl-v10.php
 ******************************************************************/
package com.Funergy.ban.Handlers;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Funergy
 *
 */
public class MuteHandler {
	public static ArrayList<String> list = new ArrayList<String>();
	
	public static void addPlayer(String pname){
		list.add(pname);
	}
	public static void removePlayer(ProxiedPlayer p){
		list.remove(p.getName());
		p.sendMessage(new TextComponent(ChatColor.RED+"You have been unmuted!"));
	}
	public static void removePlayerSilent(String name){
		list.remove(name);
	}
	public static boolean containsInList(String pname){
		return list.contains(pname);
	}

}
