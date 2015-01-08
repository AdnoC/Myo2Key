package myo2key;

/**
 * A modifier that acts as a toggle switch in conjunction with another modifier.
 * It sends an on or off actionScale to the nested modifier continuously regardless
 * of what it recieves.
 *
 * @author Adam Cutler
 * @version 
 */
public class ToggleMapModifier implements MyoMapModifier {

  /**
   * The default cooldown.
   */
  protected static long DEFAULT_COOLDOWN_SIZE = 10;

  /**
   * The nested modifier which is actually used to modify the action.
   */
  protected MyoMapModifier nestedMod;

  /**
   * How long it takes before the switch can be toggled.
   */
  protected long cooldownSize;

  /**
   * How long the cooldown has remaining.
   */
  private long cooldown;

  /**
   * The current state of the toggle switch.
   */
  private boolean toggleState = false;

  /**
   * Constructor.
   * @param mod The modifier to pass the on or off signal to.
   * @param cooldown How long it takes before the switch can be toggled.
   */
  public ToggleMapModifier(MyoMapModifier mod, long cooldown) {
    nestedMod = mod;
    cooldownSize = cooldown;
  }

  /**
   * Constructor. Uses default cooldown.
   * @param mod The modifier to pass the on or off signal to.
   */
  public ToggleMapModifier(MyoMapModifier mod) {
    this(mod, -1);
  }




  /**
   * Modifies the actionScale using the nested modifier, passing 1 or 0 depending
   * on the switch's state. Also modifies the switch state if it is not on cooldown.
   * @param actionScale The current scale of the action.
   * @param delta How long since the last time this ws called.
   * @return The modified actionScale.
   */
  public double modifyAction(double actionScale, long delta) {
    long tmp = cooldown;
    cooldown -= delta;
    delta -= tmp;

    if(cooldown <= 0 && actionScale > 0) {
      toggleState = !toggleState;
      cooldown = getCooldown();
      // Handle very long deltas (relative to cooldown)
      if(delta / getCooldown() > 0 && (delta / getCooldown()) % 2 == 0) {
        toggleState = !toggleState;
      }
    }

    return nestedMod.modifyAction(toggleState ? 1.0 : 0.0, delta);
  }

  /**
   * Accessor for cooldown.
   * @return The cooldown for this object.
   */
  public long getCooldown() {
    if(cooldownSize == -1)
      return DEFAULT_COOLDOWN_SIZE;
    else
      return cooldownSize;
  }
}
