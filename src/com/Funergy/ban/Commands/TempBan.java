/*******************************************************************
 * Copyright (c) 2014 Soulpoint Company
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS 
 * ECLIPSE PUBLIC LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR 
 * DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT’S ACCEPTANCE
 * OF THIS AGREEMENT. The full license is available at:
 * http://www.eclipse.org/org/documents/epl-v10.php
 ******************************************************************/
package com.Funergy.ban.Commands;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import com.Funergy.ban.Handlers.BanHandler;
import com.Funergy.ban.Handlers.RankHandler;
import com.Funergy.ban.MySQL.MySQL;

/**
 * @author Funergy
 *
 */
public class TempBan extends Command{
	public MySQL mysql;
	public TempBan() {
		super("tempban");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer staff = (ProxiedPlayer) sender;
		String s = RankHandler.getRank(staff.getUniqueId().toString());
		if(s.equalsIgnoreCase("Mod")||s.equalsIgnoreCase("Admin")||s.equalsIgnoreCase("Developer")||s.equalsIgnoreCase("Owner")){	
		if(args.length < 5){
			sender.sendMessage(new TextComponent(ChatColor.RED+"Usage: /tempban <Playername> <days> <hours> <minutes> <Reason>"));
			return;
		}
		int days = 0;
		int hours = 0;
		int minutes = 0;
		try {
			days = Integer.parseInt(args[1]);
			hours = Integer.parseInt(args[2]);
			minutes = Integer.parseInt(args[3]);
		} catch (Exception e) {
			sender.sendMessage(new TextComponent(ChatColor.RED+"ERROR: enter a number!"));
			return;
		}
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
		
		StringBuilder msgBuilder = new StringBuilder();
	    
	     for (int i = 4; i < args.length; i++) {
	             msgBuilder.append(args[i]).append(" ");
	     }
	    
	     String reason = msgBuilder.toString().trim();
			mysql = new MySQL();
	     if(!mysql.isBanned(mysql.getUUIDFromName(args[0]))){
	    Long l = TimeUnit.DAYS.toMillis(days)+TimeUnit.HOURS.toMillis(hours)+TimeUnit.MINUTES.toMillis(minutes)+System.currentTimeMillis();
		if(p != null){
		mysql.tempBan(p.getUniqueId().toString(), p.getName(), reason,l);
		BanHandler.addPlayer(false, p.getUniqueId().toString(), l, reason);
		mysql.disconect();
		p.disconnect(new TextComponent(ChatColor.GRAY+"You have been"+ChatColor.RED+" tempbanned"+ChatColor.GRAY+" from the SP-Network. "
				+ "\n for "+days+" days "+hours+" hours "+minutes+" minutes!"
				+ "\nReason: "+ChatColor.GREEN+reason+ChatColor.GRAY+""
						+ "\nUUID: "+ChatColor.GREEN+p.getUniqueId().toString()
						+ChatColor.GRAY+"\nApply for ban appeal at:"+ChatColor.RED+" soulpoint.eu"));
		}else{
			mysql = new MySQL();
			if(mysql.ContainsName(args[0])){
			mysql.tempBan(mysql.getUUIDFromName(args[0]), args[0],reason, l);
			BanHandler.addPlayer(false, mysql.getUUIDFromName(args[0]), l, reason);
			}else{
				sender.sendMessage(new TextComponent(ChatColor.RED+"Cannot find player in user database!"));
				return;
			}
			mysql.disconect();
		}
		for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
			String s2 = players.getUniqueId().toString();
			if(s2.equalsIgnoreCase("Builder")||s2.equalsIgnoreCase("Mod")||s2.equalsIgnoreCase("Admin")||s2.equalsIgnoreCase("Developer")||s2.equalsIgnoreCase("Owner")){	
			 players.sendMessage(new TextComponent(ChatColor.RED+"Soulpoint: "+ChatColor.GREEN+sender.getName()+" Has tempbanned "+ChatColor.YELLOW+args[0]));
			 players.sendMessage(new TextComponent(ChatColor.GREEN+"for "+days+" days "+hours+" hours "+minutes+" minutes!"));
			
				}
			
		}
		
		}else{
			sender.sendMessage(new TextComponent(ChatColor.RED+"Player is already banned!"));
		}
		}
	}

}
