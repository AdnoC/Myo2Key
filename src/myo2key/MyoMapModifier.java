package myo2key;

/**
 * Modifies the behavior of a mapping, changing when it is allowed to fire and 
 * how it does so besides just the trigger conditions.
 * @author Adam Cutler
 */
public interface MyoMapModifier {

  /**
   * Modifies the scale taken from the sources.
   * @param actionScale The current modifier for how the action will be called.
   * @param delta How long since this was last called.
   * @return The modified actionScale.
   */
  public double modifyAction(double actionScale, long delta);

}
