package com.forgeessentials.api.economy;

import com.forgeessentials.api.APIRegistry;
import com.forgeessentials.api.UserIdent;
import com.forgeessentials.core.misc.TranslatedCommandException;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.function.Function;

public class WalletHelper {
  public static void payOrThrow(EntityPlayerMP player, long amount, Function<Long, Long> priceMap) throws TranslatedCommandException {
    Wallet wallet = APIRegistry.economy.getWallet(UserIdent.get(player.getUniqueID()));
    long price = priceMap.apply(amount);
    if(!wallet.withdraw(price)){
      String money = APIRegistry.economy.currency(price);
      throw new TranslatedCommandException("you don't have enough "+money+": "+price+" "+money+" are required");
    }
  }
}
