package com.jordna.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.jordna.commands.WarpCommands;
import com.jordna.managers.WarpManager;

public class OneTimeWarps extends JavaPlugin
{

    private WarpManager warpManager;
    
    @Override
    public void onEnable()
    {
	warpManager = new WarpManager(this);
	warpManager.parseWarps();
	
	getCommand("owarp").setExecutor(new WarpCommands(this));
    }
    
    @Override
    public void onDisable()
    {
	warpManager.saveWarps();
    }
    
    public WarpManager getWarpManager()
    {
        return warpManager;
    }
    
}