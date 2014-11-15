package com.Funergy.ban.Events;

import com.Funergy.ban.Handlers.MuteHandler;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MuteEvent implements Listener{
	
	@EventHandler
	public void onChatEvent(ChatEvent e){
	
		 ProxiedPlayer p = (ProxiedPlayer) e.getSender();
		 if(!e.getMessage().startsWith("/")){
		if(MuteHandler.list.contains(p.getName())){
			e.setCancelled(true);
			p.sendMessage(new TextComponent(ChatColor.RED+"Cannot chat when you're muted!"));
		}
		 }

	}

}
