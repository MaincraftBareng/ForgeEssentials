package com.forgeessentials.commands.util;

import com.forgeessentials.api.APIRegistry;
import com.forgeessentials.api.UserIdent;
import com.forgeessentials.api.economy.Wallet;
import com.forgeessentials.core.misc.TaskRegistry;
import com.forgeessentials.util.ServerUtil;
import net.minecraft.entity.player.EntityPlayerMP;

public class CreditGivingTickTask implements TaskRegistry.TickTask {

  long lastGave = System.currentTimeMillis();

  @Override
  public boolean tick() {
    long now = System.currentTimeMillis();
    if(now - lastGave >= 5000){
      for(EntityPlayerMP player: ServerUtil.getPlayerList()){
        Wallet wallet = APIRegistry.economy.getWallet(UserIdent.get(player.getUniqueID()));
        wallet.add(5);
      }
      lastGave = now;
    }
    return false;
  }

  @Override
  public boolean editsBlocks() {
    return false;
  }
}
