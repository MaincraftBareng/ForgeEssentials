package com.forgeessentials.teleport;

import com.forgeessentials.core.misc.PriceMaps;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

import com.forgeessentials.commons.selections.WarpPoint;
import com.forgeessentials.core.commands.ForgeEssentialsCommandBase;
import com.forgeessentials.core.misc.TeleportHelper;
import com.forgeessentials.core.misc.TranslatedCommandException;
import com.forgeessentials.util.PlayerInfo;

import java.util.function.Function;

public class CommandBack extends ForgeEssentialsCommandBase
{
    //todo: read from config
    private Function<Long,Long> tpPricing = PriceMaps.linear(3);

    @Override
    public String getName()
    {
        return "feback";
    }

    @Override
    public String[] getDefaultAliases()
    {
        return new String[] { "back" };
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/back: Teleport you to your last death or teleport location.";
    }

    @Override
    public boolean canConsoleUseCommand()
    {
        return false;
    }

    @Override
    public DefaultPermissionLevel getPermissionLevel()
    {
        return DefaultPermissionLevel.ALL;
    }

    @Override
    public String getPermissionNode()
    {
        return TeleportModule.PERM_BACK;
    }

    @Override
    public void processCommandPlayer(MinecraftServer server, EntityPlayerMP sender, String[] args) throws CommandException
    {
        PlayerInfo pi = PlayerInfo.get(sender.getPersistentID());
        WarpPoint point = null;
        if (PermissionAPI.hasPermission(sender, TeleportModule.PERM_BACK_ONDEATH))
            point = pi.getLastDeathLocation();
        if (point == null)
            point = pi.getLastTeleportOrigin();
        if (point == null)
            throw new TranslatedCommandException("You have nowhere to get back to");

        TeleportHelper.paidTeleport(sender, point, tpPricing);
    }

}
