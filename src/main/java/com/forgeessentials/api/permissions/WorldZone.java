package com.forgeessentials.api.permissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.forgeessentials.api.APIRegistry;
import com.forgeessentials.util.selections.WorldArea;
import com.forgeessentials.util.selections.WorldPoint;

import net.minecraft.entity.player.EntityPlayer;

/**
 * {@link WorldZone} covers the entirety of a world. Third lowest in priority with next being {@link ServerZone}.
 * 
 * @author Bjoern Zeutzheim
 */
public class WorldZone extends Zone {
	
	private ServerZone serverZone;

	private int dimensionID;

	private List<AreaZone> areaZones = new ArrayList<AreaZone>();

	public WorldZone(ServerZone serverZone, int dimensionID)
	{
		super(serverZone.getRootZone().getNextZoneID());
		this.dimensionID = dimensionID;
		this.serverZone = serverZone;
		this.serverZone.addWorldZone(this);
	}

	@Override
	public boolean isPlayerInZone(EntityPlayer player)
	{
		return player.dimension == dimensionID;
	}
	
	@Override
	public boolean isInZone(WorldPoint point)
	{
		return point.getDimension() == dimensionID;
	}

	@Override
	public boolean isInZone(WorldArea area)
	{
		return area.getDimension() == dimensionID;
	}

	@Override
	public boolean isPartOfZone(WorldArea area)
	{
		return area.getDimension() == dimensionID;
	}

	@Override
	public String getName()
	{
		return "WORLD_" + dimensionID;
	}

	@Override
	public Zone getParent()
	{
		return serverZone;
	}

	public ServerZone getServerZone()
	{
		return serverZone;
	}

	public int getDimensionID()
	{
		return dimensionID;
	}

	public AreaZone getAreaZone(String areaName)
	{
		for (AreaZone areaZone : areaZones)
		{
			if (areaZone.getShotName().equals(areaName))
			{
				return areaZone;
			}
		}
		return null;
	}

	public void removeAreaZone(String areaName)
	{
		areaZones.remove(areaName);
	}

	public Collection<AreaZone> getAreaZones()
	{
		return areaZones;
	}

	public void sortAreaZones()
	{
		// TODO: Sort for priorities
		throw new RuntimeException("Not yet implemented");
	}

	void addAreaZone(AreaZone areaZone)
	{
		areaZones.add(areaZone);
	}

}
