/*******************************************************************
 * Copyright (c) 2014 Soulpoint Company
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS 
 * ECLIPSE PUBLIC LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR 
 * DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT’S ACCEPTANCE
 * OF THIS AGREEMENT. The full license is available at:
 * http://www.eclipse.org/org/documents/epl-v10.php
 ******************************************************************/
package com.Funergy.ban.Events;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.Funergy.ban.Handlers.MuteHandler;
import com.Funergy.ban.Handlers.RankHandler;
import com.Funergy.ban.MySQL.MySQL;

/**
 * @author Funergy
 *
 */
public class JoinEvent implements Listener{
	public MySQL mysql;
	@EventHandler
	public void joinevent(PostLoginEvent e){
		mysql = new MySQL();
		RankHandler.putInHashMap(e.getPlayer().getUniqueId().toString());
		if(!mysql.ContainsName(e.getPlayer().getUniqueId().toString())){
		    mysql.addPlayerToDB(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString());
		
		}
		if(e.getPlayer().getName()!= mysql.getNameFromUUID(e.getPlayer().getUniqueId().toString())){
				mysql.updateName(e.getPlayer().getUniqueId().toString(),e.getPlayer().getName());
		}
		
			
		if(!MuteHandler.containsInList(e.getPlayer().getName())){
		if(mysql.isMuted(e.getPlayer().getUniqueId().toString())){
			MuteHandler.addPlayer(e.getPlayer().getName());
		}
		}else{
			if(!mysql.isMuted(e.getPlayer().getUniqueId().toString())){
				MuteHandler.removePlayerSilent(e.getPlayer().getName());
			}
		}
		e.getPlayer().sendMessage(new TextComponent(RankHandler.getRank(e.getPlayer().getUniqueId().toString())));
		mysql.disconect();
	}

}
