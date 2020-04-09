package com.forgeessentials.core.misc;

import java.util.function.Function;

public class PriceMaps {
  public static Function<Long, Long> linear(final double multiplier){
    return l -> (long) (l*multiplier);
  }
  public static Function<Long, Long> powered(final double power, final double multiplier, final double offset){
    return l -> (long) (Math.pow(l,power)*multiplier + offset);
  }
}
