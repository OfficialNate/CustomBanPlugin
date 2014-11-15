package com.Funergy.ban;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import com.Funergy.ban.Commands.BanCommand;
import com.Funergy.ban.Commands.KickCommand;
import com.Funergy.ban.Commands.MuteCommand;
import com.Funergy.ban.Commands.TempBan;
import com.Funergy.ban.Commands.UnbanCommand;
import com.Funergy.ban.Commands.UnmuteCommand;
import com.Funergy.ban.Events.JoinEvent;
import com.Funergy.ban.Events.MuteEvent;
import com.Funergy.ban.Events.PreLoginevent;
import com.Funergy.ban.Events.ServerkickEvent;
import com.Funergy.ban.MySQL.MySQL;

public class BanSystem extends Plugin{
	public MySQL mysql;
	@Override
	public void onEnable() {
		ProxyServer.getInstance().getPluginManager().registerListener(this, new MuteEvent());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinEvent());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerkickEvent());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new PreLoginevent());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new BanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new UnbanCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new KickCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new MuteCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new UnmuteCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new TempBan());


		
	}
	

}
