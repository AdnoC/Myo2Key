package myo2key;

/**
 * A placeholder modifier that is used when you don't want to use a modifier.
 *
 * @author Adam Cutler
 * @version 
 */
public class TransparentMapModifier implements MyoMapModifier {

  /**
   * Just returns actionScale without modifying anything.
   * @param actionScale The current scale of the action.
   * @param delta How long since the last time this ws called.
   * @return The (not) modified actionScale.
   */
  public double modifyAction(double actionScale, long delta) {
    return actionScale;
  }
}
