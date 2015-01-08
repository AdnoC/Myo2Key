package myo2key;

/**
 * A modifier that activates once then needs to not be active in before it can activate again.
 *
 * @author Adam Cutler
 * @version 
 */
public class OneShotMapModifier implements MyoMapModifier {

  /**
   * The default cooldown.
   */
  protected static long DEFAULT_COOLDOWN_SIZE = 10;

  /**
   * How long the cooldown for this instance is.
   */
  protected final long cooldownSize;

  /**
   * How much cooldown remains.
   */
  private long cooldown;

  /**
   * Whether or not this is ready to fire.
   */
  private boolean isSet = true;

  /**
   * Constructor
   * @param cooldown How long this has to be inactive before it is reset.
   */
  public OneShotMapModifier(long cooldown) {
    cooldownSize = cooldown;
  }

  /**
   * Constructor that sets the cooldown to default.
   */
  public OneShotMapModifier() {
    this(-1);
  }


  /**
   * Modifies the action so it only occurs if this is ready. If it is not, it
   * goes on cooldown which only goes down while the source is not activated.
   * @param actionScale The current scale of the action.
   * @param delta How long since the last time this ws called.
   * @return The modified actionScale.
   */
  public double modifyAction(double actionScale, long delta) {
    if(isSet && actionScale > 0) {
      isSet = false;
      return actionScale;
    } else if(!isSet && actionScale == 0) {
      cooldown -= delta;
      if(cooldown <= 0) {
        cooldown = getCooldown();;
        isSet = true;
      }
      return 0.0;
    } else {
      return 0.0;
    }

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
