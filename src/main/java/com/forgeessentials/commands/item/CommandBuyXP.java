package com.forgeessentials.commands.item;

import com.forgeessentials.api.APIRegistry;
import com.forgeessentials.api.UserIdent;
import com.forgeessentials.api.economy.Wallet;
import com.forgeessentials.commands.player.CommandAFK;
import com.forgeessentials.core.commands.ForgeEssentialsCommandBase;
import com.forgeessentials.util.output.ChatOutputHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.permission.DefaultPermissionLevel;

public class CommandBuyXP extends ForgeEssentialsCommandBase {
  @Override
  public String getName() {
    return "buyxp";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return "buyxp [numberofxp]: buy a number of xp with the price of 60 credits each";
  }

  @Override
  public boolean canConsoleUseCommand() {
    return false;
  }

  @Override
  public String getPermissionNode() {
    return CommandAFK.PERM;
  }

  @Override
  public DefaultPermissionLevel getPermissionLevel()
  {
    return DefaultPermissionLevel.ALL;
  }

  @Override
  public void processCommandPlayer(MinecraftServer server, EntityPlayerMP sender, String[] args) throws CommandException
  {
    if(args.length == 1 ){
      int n = Integer.parseInt(args[0]);
      int price = n*60;
      Wallet wallet = APIRegistry.economy.getWallet(UserIdent.get(sender.getUniqueID()));
      if(wallet.withdraw(price)){
        sender.addExperience(n);
        ChatOutputHandler.chatNotification(sender, "exchanged "+price+" credits for "+n+" xp");
      }else{
        ChatOutputHandler.chatNotification(sender, "not enough credits, needed: "+price+" have: "+wallet.get());
      }
    }
  }
}
