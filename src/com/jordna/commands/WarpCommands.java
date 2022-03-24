package com.jordna.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jordna.main.OneTimeWarps;
import com.jordna.warps.Warp;

import net.md_5.bungee.api.ChatColor;

public class WarpCommands implements CommandExecutor
{

    private OneTimeWarps main;
    
    public WarpCommands(OneTimeWarps instance)
    {
	main = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
	if (!(sender instanceof Player))
	{
	    System.out.println("Only a player can run a warp command");
	    return true;
	}
	
	Player player = (Player) sender;
	
	if (cmd.getName().equalsIgnoreCase("owarp"))
	{
	    if (args.length < 1)
	    {
		player.sendMessage(ChatColor.RED + "Correct usage is: /owarp [warp name]");
		return true;
	    }
	    
	    if (args[0].equalsIgnoreCase("add"))
	    {
		if (args.length < 2)
		{
		    player.sendMessage(ChatColor.RED + "Correct usage is: /owarp add [warp name]");
		    return true;
		}
		
		if (!player.hasPermission("onetimewarps.add"))
		{
		    player.sendMessage(ChatColor.RED + "You do not have permission to run that command.");
		    return true;
		}
		
		boolean b = main.getWarpManager().addWarp(args[1], player.getLocation());
		
		if (!b)
		{
		    player.sendMessage(ChatColor.RED + "That warp already exists!");
		    return true;
		}
		
		player.sendMessage(ChatColor.BLUE + "Added warp '" + args[1] + "'");
		return true;
	    }
	    
	    if (!player.hasPermission("onetimewarps.warp"))
	    {
		player.sendMessage(ChatColor.RED + "You do not have permission to run that command.");
		return true;
	    }
	    
	    Warp warp = main.getWarpManager().getWarp(args[0]);
	   
	    if (warp == null)
	    {
		player.sendMessage(ChatColor.RED + "That warp does not exist!");
		return true;
	    }
	    
	    player.teleport(warp.location);
	    boolean b = main.getWarpManager().removeWarp(args[0]);
	    player.sendMessage(ChatColor.BLUE + "Teleported to " + warp.name);
	    if (!b)
	    {
		player.sendMessage(ChatColor.RED + "Failed to remove warp '" + warp.name + "'");
	    }
	    
	    return true;
	}
	
	return false;
    }

}
