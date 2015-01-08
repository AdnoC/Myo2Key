
package myo2key;

/**
 * A modifier that activates a certain number of times at once then needs to not be active in before it can activate again.
 *
 * @author Adam Cutler
 * @version
 */
public class MultiShotMapModifier implements MyoMapModifier {

  /**
   * The default cooldown.
   */
  protected static final long DEFAULT_COOLDOWN_SIZE = 10;

  /**
   * How long the cooldown for this instance is.
   */
  protected final long cooldownSize;

  /**
   * How much cooldown remains.
   */
  private long cooldown;

  /**
   * The number of times this activates.
   */
  protected int numShots;

  /**
   * Whether or not this is ready to fire.
   */
  private boolean isSet = true;

  /**
   * Constructor
   * @param num The number of times this activates.
   * @param cooldown How long this has to be inactive before it is reset.
   */
  public MultiShotMapModifier(int num, long cooldown) {
    numShots = num;
    cooldownSize = cooldown;
  }

  /**
   * Constructor that sets the cooldown to default.
   * @param num The number of times this activates.
   */
  public MultiShotMapModifier(int num) {
    this(num, -1);
  }


  /**
   * Multiplies the action scale by numShots if this is ready. If not it goes on
   * a cooldown which only goes down while actionScale is not activated.
   * @param actionScale The current scale of the action.
   * @param delta How long since the last time this ws called.
   * @return The modified actionScale.
   */
  public double modifyAction(double actionScale, long delta) {
    if(isSet && actionScale > 0) {
      isSet = false;
      return 1.0;
    } else if(!isSet && actionScale == 0) {
      cooldown -= delta;
      if(cooldown <= 0) {
        cooldown = getCooldown();
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
