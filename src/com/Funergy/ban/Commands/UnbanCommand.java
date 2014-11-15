/*******************************************************************
 * Copyright (c) 2014 Soulpoint Company
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS 
 * ECLIPSE PUBLIC LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR 
 * DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT’S ACCEPTANCE
 * OF THIS AGREEMENT. The full license is available at:
 * http://www.eclipse.org/org/documents/epl-v10.php
 ******************************************************************/
package com.Funergy.ban.Commands;

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
public class UnbanCommand extends Command{
	public MySQL mysql;
	public UnbanCommand() {
		super("unban");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		mysql=new MySQL();
		ProxiedPlayer staff = (ProxiedPlayer) sender;
		String s = RankHandler.getRank(staff.getUniqueId().toString());
		mysql.disconect();
		if(s.equalsIgnoreCase("Mod")||s.equalsIgnoreCase("Admin")||s.equalsIgnoreCase("Developer")||s.equalsIgnoreCase("Owner")){	
		if(args.length < 1){
			sender.sendMessage(new TextComponent(ChatColor.RED+"Usage: /unban <Playername>"));
			return;
		}
			mysql = new MySQL();
			if(mysql.ContainsName(args[0])){
			if(mysql.isBanned(mysql.getUUIDFromName(args[0]))){
				mysql.unBan(mysql.getUUIDFromName(args[0]));
				BanHandler.removePlayer(mysql.getUUIDFromName(args[0]));
				mysql.disconect();
			}else{
				mysql.disconect();
				sender.sendMessage(new TextComponent(ChatColor.RED+args[0]+" is not banned!"));
				return;
			}
			}else{
				mysql.disconect();
				sender.sendMessage(new TextComponent(ChatColor.RED+"Player is not in ban/user database"));
				return;
			}
			
		
		
		for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
			
			String s2 = RankHandler.getRank(players.getUniqueId().toString());
			if(s2.equalsIgnoreCase("Builder")||s2.equalsIgnoreCase("Mod")||s2.equalsIgnoreCase("Admin")||s2.equalsIgnoreCase("Developer")||s2.equalsIgnoreCase("Owner")){	
			 players.sendMessage(new TextComponent(ChatColor.RED+"Soulpoint: "+ChatColor.GREEN+sender.getName()+" Has unbanned "+ChatColor.YELLOW+args[0]));
			 
				}
		}
		
		}
		
		
	}

}
