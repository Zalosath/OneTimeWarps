package com.jordna.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.jordna.main.OneTimeWarps;
import com.jordna.warps.Warp;

public class WarpManager
{

    public List<Warp> warps = new ArrayList<Warp>();
    
    private OneTimeWarps main;
    public WarpManager(OneTimeWarps instance)
    {
	main = instance;
    }
 
    public void parseWarps()
    {
	for (String key : main.getConfig().getKeys(false))
	{
	    Warp warp = new Warp();
	    warp.name = key;
	    
	    World world = Bukkit.getWorld(main.getConfig().getString(key + ".location.world"));
	    double x = main.getConfig().getDouble(key + ".location.x");
	    double y = main.getConfig().getDouble(key + ".location.y");
	    double z = main.getConfig().getDouble(key + ".location.z");
	    float yaw = (float)main.getConfig().getDouble(key + ".location.yaw");
	    float pitch = (float)main.getConfig().getDouble(key + ".location.pitch");
	    
	    Location location = new Location(world, x, y, z, yaw, pitch);
	    
	    warp.location = location;
	    
	    warps.add(warp);
	}
    }
    
    public Warp getWarp(String name)
    {
	for (Warp warp : warps)
	{
	    if (warp.name.equalsIgnoreCase(name))
	    {
		return warp;
	    }
	}
	return null;
    }
    
    public boolean removeWarp(String name)
    {
	Warp warp = getWarp(name);
	
	if (warp == null || !warps.contains(warp))
	{
	    return false;
	}
	
	warps.remove(warp);
	saveWarps();
	return true;
    }
    
    public boolean addWarp(String name, Location location)
    {
	Warp foundWarp = getWarp(name);
	if (foundWarp != null)
	{
	    return false;
	}
	
	Warp warp = new Warp();
	warp.name = name;
	warp.location = location;
	
	warps.add(warp);
	
	saveWarps();
	return true;
    }
    
    public void saveWarps()
    {
	File config = new File(main.getDataFolder(), "config.yml");
	
	if (config.exists())
	{
	    config.delete();
	    main.reloadConfig();
	}
	
	for (Warp warp : warps)
	{
	    main.getConfig().set(warp.name + ".location.world", warp.location.getWorld().getName());
	    main.getConfig().set(warp.name + ".location.x", warp.location.getX());
	    main.getConfig().set(warp.name + ".location.y", warp.location.getY());
	    main.getConfig().set(warp.name + ".location.z", warp.location.getZ());
	    main.getConfig().set(warp.name + ".location.yaw", warp.location.getYaw());
	    main.getConfig().set(warp.name + ".location.pitch", warp.location.getPitch());
	}
	
	main.saveConfig();
    }
}