package myo2key;

/**
 * A modifier that allows a map to be fired on demand, but is put on cooldown after firing.
 * @author Adam Cutler
 */
public class OnDemandMapModifier implements MyoMapModifier {
  // The default cooldown.
  protected static final long DEFAULT_COOLDOWN_SIZE = 10;

  // How long the cooldown for this instance is.
  private final long cooldownSize;

  // How much cooldown remains.
  private long cooldown;

  /**
   * Default constructor which uses the default cooldown size.
   */
  public OnDemandMapModifier() {
    cooldownSize = DEFAULT_COOLDOWN_SIZE;
    cooldown = 0;
  }

  /**
   * Constructor which uses the specified cooldown size.
   * @param cdSize How long the cooldown for this ODMM should be in milliseconds.
   */
  public OnDemandMapModifier(long cdSize) {
    cooldownSize = cdSize;
    cooldown = 0;
  }

  /**
   * Modifies the action so that it only goes of if this isn't on cooldown. Causes
   * the action to fire multiple times if this would have gone off cooldown in the time since it
   * was last called.
   * @param actionScale The current scale of the action.
   * @param delta How long since the last time this ws called.
   * @return The modified actionScale.
   */
  public double modifyAction(double actionScale, long delta) {
    System.out.println("delta: " + delta + " cd: " + cooldown);
    if(cooldown > 0) {
      if(delta < cooldown) {
        cooldown -= delta;
        return 0;
      } else {
        int timesFired = 1;
        delta -= cooldown;
        timesFired += (int) (delta / cooldownSize);
        cooldown =  cooldownSize;
        if(actionScale == 0) {
          cooldown = 0;
        }
        return actionScale * timesFired;
      }
    } else if(actionScale != 0) {
      int timesFired = 1 + (int) (delta / cooldownSize);
      cooldown =  cooldownSize;
      return actionScale * timesFired;
    } else {
      return 0;
    }
  }
}
