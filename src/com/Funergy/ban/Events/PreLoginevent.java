/*******************************************************************
 * Copyright (c) 2014 Soulpoint Company
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS 
 * ECLIPSE PUBLIC LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR 
 * DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT’S ACCEPTANCE
 * OF THIS AGREEMENT. The full license is available at:
 * http://www.eclipse.org/org/documents/epl-v10.php
 ******************************************************************/
package com.Funergy.ban.Events;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import com.Funergy.ban.Handlers.BanHandler;
import com.Funergy.ban.MySQL.MySQL;

/**
 * @author Funergy
 *
 */
public class PreLoginevent implements Listener{
	
	public MySQL mysql;
	@EventHandler
	public void joinevent(PreLoginEvent e){
		mysql = new MySQL();
		String UUID = mysql.getUUIDFromName(e.getConnection().getName());
		if(!BanHandler.isBanned(UUID)){
		if(mysql.isBanned(UUID)){
			String reason = mysql.getReason(UUID);
		if(mysql.isPermBanned(UUID)){
			BanHandler.addPlayer(true, UUID, null, reason);
			e.getConnection().disconnect(new TextComponent(ChatColor.GRAY+"You have been"+ChatColor.RED+" permbanned"+ChatColor.GRAY+" from the SP-Network."
					+ "\nReason: "+ChatColor.GREEN+reason+ChatColor.GRAY+""
							+ "\nUUID: "+ChatColor.GREEN+UUID
							+ChatColor.GRAY+"\n \nApply for ban appeal at:"+ChatColor.RED+" soulpoint.eu"));		
			}else{
				Long l = mysql.getTempBanLength(UUID);
				if(l > System.currentTimeMillis()){
				BanHandler.addPlayer(false, UUID, l, reason);

					Long length = mysql.getTempBanLength(UUID)-System.currentTimeMillis();
				Long seconds = TimeUnit.MILLISECONDS.toSeconds(length) - 
		    		    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(length));  
				  
				Long days = TimeUnit.MILLISECONDS.toDays(length);
				Long hours = TimeUnit.MILLISECONDS.toHours(TimeUnit.MILLISECONDS.toMillis(length)-TimeUnit.DAYS.toMillis(days));
				Long minutes = TimeUnit.MILLISECONDS.toMinutes(TimeUnit.MILLISECONDS.toMillis(length)-TimeUnit.DAYS.toMillis(days)-TimeUnit.HOURS.toMillis(hours));
				String time;
				
				if(days == 0){
					time = ""+hours+" hours, "+minutes+" minutes, "+ seconds+" seconds!";
				}else{
					time = ""+days+" days, "+hours+" hours, "+ minutes+" minutes!";
				}
		    	
				e.getConnection().disconnect(new TextComponent(ChatColor.GRAY+"You have been"+ChatColor.RED+" tempbanned"+ChatColor.GRAY+" from the SP-Network. "
						+ "\n for "+time
						+ "\nReason: "+reason+ChatColor.GRAY+""
								+ "\nUUID: "+ChatColor.GREEN+UUID
								+ChatColor.GRAY+"\nApply for ban appeal at:"+ChatColor.RED+" soulpoint.eu"));
				

			}else{
			mysql.unBan(mysql.getUUIDFromName(e.getConnection().getName()));
			}
			}
		}//NON MYSQL VERSION IF HE CONTAINS ON THE HASHMAP!
		}else{
			String reason = BanHandler.getReason(UUID);
			if(BanHandler.isPerm(UUID)){
				e.getConnection().disconnect(new TextComponent(ChatColor.GRAY+"You have been"+ChatColor.RED+" permbanned"+ChatColor.GRAY+" from the SP-Network."
						+ "\nReason: "+ChatColor.GREEN+reason+ChatColor.GRAY+""
								+ "\nUUID: "+ChatColor.GREEN+UUID
								+ChatColor.GRAY+"\n \nApply for ban appeal at:"+ChatColor.RED+" soulpoint.eu"));		
				}else{
					Long l = BanHandler.getTime(UUID);
					if(l > System.currentTimeMillis()){
					BanHandler.addPlayer(false, UUID, l, reason);

						Long length = BanHandler.getTime(UUID)-System.currentTimeMillis();
					Long seconds = TimeUnit.MILLISECONDS.toSeconds(length) - 
			    		    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(length));  
					  
					Long days = TimeUnit.MILLISECONDS.toDays(length);
					Long hours = TimeUnit.MILLISECONDS.toHours(TimeUnit.MILLISECONDS.toMillis(length)-TimeUnit.DAYS.toMillis(days));
					Long minutes = TimeUnit.MILLISECONDS.toMinutes(TimeUnit.MILLISECONDS.toMillis(length)-TimeUnit.DAYS.toMillis(days)-TimeUnit.HOURS.toMillis(hours));
					String time;
					
					if(days == 0){
						time = ""+hours+" hours, "+minutes+" minutes, "+ seconds+" seconds!";
					}else{
						time = ""+days+" days, "+hours+" hours, "+ minutes+" minutes!";
					}
			    	
					e.getConnection().disconnect(new TextComponent(ChatColor.GRAY+"You have been"+ChatColor.RED+" tempbanned"+ChatColor.GRAY+" from the SP-Network. "
							+ "\n for "+time
							+ "\nReason: "+reason+ChatColor.GRAY+""
									+ "\nUUID: "+ChatColor.GREEN+UUID
									+ChatColor.GRAY+"\nApply for ban appeal at:"+ChatColor.RED+" soulpoint.eu"));
					

				}else{
				mysql.unBan(mysql.getUUIDFromName(e.getConnection().getName()));
				BanHandler.removePlayer(UUID);
				}
				}	
		}
		
		mysql.disconect();
	}

}
