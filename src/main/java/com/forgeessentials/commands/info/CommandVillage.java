package com.forgeessentials.commands.info;

import com.forgeessentials.commands.player.CommandAFK;
import com.forgeessentials.core.commands.ForgeEssentialsCommandBase;
import com.forgeessentials.util.output.ChatOutputHandler;
import com.google.common.base.Predicate;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.server.permission.DefaultPermissionLevel;

import javax.annotation.Nullable;
import java.util.List;

public class CommandVillage extends ForgeEssentialsCommandBase {
  @Override
  public String getName() {
    return "infovillage";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return "infovillage";
  }

  @Override
  public String[] getDefaultAliases()
  {
    return new String[] { "village" };
  }

  @Override
  public boolean canConsoleUseCommand() {
    return true;
  }

  @Override
  public String getPermissionNode() {
    return CommandAFK.PERM;
  }

  public DefaultPermissionLevel getPermissionLevel()
  {
    return DefaultPermissionLevel.ALL;
  }

  @Override
  public void processCommandPlayer(MinecraftServer server, EntityPlayerMP sender, String[] args) throws CommandException {
    World world = server.getWorld(0);
    Village village = world.villageCollection.getNearestVillage(sender.getPosition(), 500);
    BlockPos center = village.getCenter();
    int villageRadius = village.getVillageRadius();

    if(village == null) return;
    if(args.length == 0 ){

      ChatOutputHandler.chatNotification(sender,String.format("Center: %s, Radius: %d, Villager: %d, DoorNum: %d Reputation: %s, MatingSeason: %s, Annihilated: %s",
          center,
          villageRadius,
          village.getNumVillagers(),
          village.getNumVillageDoors(),
          village.getPlayerReputation(sender.getUniqueID()),
          village.isMatingSeason(),
          village.isAnnihilated()
      ));

    } else if (args.length == 1 && args[0].equals("watch")) {

      List<Entity> villagers = server.getWorld(0).getEntities(Entity.class, new Predicate<Entity>() {
        @Override
        public boolean apply(@Nullable Entity input) {
          return false;
        }
      });
    }
  }
}
