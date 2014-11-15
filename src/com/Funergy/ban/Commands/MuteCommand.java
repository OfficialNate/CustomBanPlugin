/*******************************************************************
 * Copyright (c) 2014 Soulpoint Company
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS 
 * ECLIPSE PUBLIC LICENSE ("AGREEMENT"). ANY USE, REPRODUCTION OR 
 * DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT’S ACCEPTANCE
 * OF THIS AGREEMENT. The full license is available at:
 * http://www.eclipse.org/org/documents/epl-v10.php
 ******************************************************************/
package com.Funergy.ban.Commands;

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
public class MuteCommand extends Command{
	public MySQL mysql;

	public MuteCommand() {
		super("mute");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer staff =(ProxiedPlayer) sender;
		String s = RankHandler.getRank(staff.getUniqueId().toString());
		if(s.equalsIgnoreCase("Mod")||s.equalsIgnoreCase("Helper")||s.equalsIgnoreCase("Admin")||s.equalsIgnoreCase("Developer")||s.equalsIgnoreCase("Owner")){	
		if(args.length < 2){
			sender.sendMessage(new TextComponent(ChatColor.RED+"Usage: /mute <Playername> <Reason>"));
			return;
		}
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
		
		StringBuilder msgBuilder = new StringBuilder();
	    
	     for (int i = 1; i < args.length; i++) {
	             msgBuilder.append(args[i]).append(" ");
	     }
	    
	     String reason = msgBuilder.toString().trim();
	     mysql = new MySQL();
	     if(!mysql.isMuted(mysql.getUUIDFromName(args[0]))){
		if(p != null){
			mysql.Mute(p.getUniqueId().toString(), p.getName(), reason);
		mysql.disconect();
		p.disconnect(new TextComponent(ChatColor.GRAY+"You have been"+ChatColor.RED+" perm-muted"+ChatColor.GRAY+" from the SP-Network."
				+ "\nReason: "+ChatColor.GREEN+reason+ChatColor.GRAY+""
						+ "\nUUID: "+ChatColor.GREEN+p.getUniqueId().toString()
						+ChatColor.GRAY+"\n \nApply for mute appeal at:"+ChatColor.RED+" soulpoint.eu"));
		}else{
			if(mysql.ContainsName(args[0])){
			mysql.Mute(mysql.getUUIDFromName(args[0]), args[0], reason);
			}else{
				sender.sendMessage(new TextComponent(ChatColor.RED+"Cannot find player in user database!"));
				return;
			}
			mysql.disconect();
		}
		for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
			
			String s2 = RankHandler.getRank(players.getUniqueId().toString());
			if(s2.equalsIgnoreCase("Builder")||s2.equalsIgnoreCase("Mod")||s2.equalsIgnoreCase("Admin")||s2.equalsIgnoreCase("Developer")||s2.equalsIgnoreCase("Owner")){	
			 players.sendMessage(new TextComponent(ChatColor.RED+"Soulpoint: "+ChatColor.GREEN+sender.getName()+" Has muted "+ChatColor.YELLOW+args[0]));
			 
				}
			
		}
		}else{
			sender.sendMessage(new TextComponent(ChatColor.RED+"Player is already muted!"));

		}
		}
		
	}

}
