/*******************************************************************
 * Copyright (c) 2014 Soulpoint Company
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS 
 * ECLIPSE PUBLIC LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR 
 * DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT’S ACCEPTANCE
 * OF THIS AGREEMENT. The full license is available at:
 * http://www.eclipse.org/org/documents/epl-v10.php
 ******************************************************************/
package com.Funergy.ban.Commands;

import com.Funergy.ban.Handlers.MuteHandler;
import com.Funergy.ban.Handlers.RankHandler;
import com.Funergy.ban.MySQL.MySQL;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author Funergy
 *
 */
public class UnmuteCommand extends Command{
	public MySQL mysql;
	public UnmuteCommand() {
		super("unmute");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer staff = (ProxiedPlayer) sender;
		String s = RankHandler.getRank(staff.getUniqueId().toString());
		if(s.equalsIgnoreCase("Mod")||s.equalsIgnoreCase("Helper")||s.equalsIgnoreCase("Admin")||s.equalsIgnoreCase("Developer")||s.equalsIgnoreCase("Owner")){	
		if(args.length < 1){
			sender.sendMessage(new TextComponent(ChatColor.RED+"Usage: /unmute <Playername>"));
			return;
		}
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
			mysql = new MySQL();
			String uuid = mysql.getUUIDFromName(args[0]);
			if(mysql.ContainsName(args[0])){
			if(mysql.isMuted(uuid)){
				if(p != null){
					MuteHandler.removePlayer(p);
				}
				
				mysql.unMute(uuid);
				
			}else{
				sender.sendMessage(new TextComponent(ChatColor.RED+args[0]+" is not muted!"));
				return;
			}
			}else{
				sender.sendMessage(new TextComponent(ChatColor.RED+"Player is not in ban/user database"));
				return;
			}
			
			mysql.disconect();
			
		
		for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
			
			String s2 = RankHandler.getRank(players.getUniqueId().toString());
			if(s2.equalsIgnoreCase("Builder")||s2.equalsIgnoreCase("Mod")||s2.equalsIgnoreCase("Admin")||s2.equalsIgnoreCase("Developer")||s2.equalsIgnoreCase("Owner")){	
			 players.sendMessage(new TextComponent(ChatColor.RED+"Soulpoint: "+ChatColor.GREEN+sender.getName()+" Has unmuted "+ChatColor.YELLOW+args[0]));
			
				}
			
		}
		}
		
		
	}
	

}
