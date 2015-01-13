package myo2key;

/**
 * Modifies the nested modifier's output so it is either a 0 or a 1.
 * Changes it to 1 whenever the output is not 0.
 *
 * @author Adam Cutler
 * @version 
 */
public class BinaryMapModifier implements NestingMapModifier {

  /**
   * The nested modifier which is actually used to modify the action.
   */
  protected MyoMapModifier nestedMod;

  /**
   * Constructor.
   * @param mod The modifier who's output will be changed to 0 or 1.
   */
  public BinaryMapModifier(MyoMapModifier mod) {
    nestedMod = mod;
  }

  /**
   * Modifies the actionScale using the nested modifier, changing the output to 0
   * if it is 0, or 1 if the output is not 0.
   * @param actionScale The current scale of the action.
   * @param delta How long since the last time this ws called.
   * @return The modified actionScale.
   */
  public double modifyAction(double actionScale, long delta) {
    return nestedMod.modifyAction(actionScale, delta) == 0 ? 0 : 1;
  }

  /**
   * Accessor for the modifier nested within.
   * @return The modifier nested within this object.
   */
  public MyoMapModifier getNestedModifier() {
    return nestedMod;
  }

}


