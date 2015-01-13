package myo2key;

/**
 * Modifies the nested modifier's output so it is multiplied by a scalar.
 *
 * @author Adam Cutler
 * @version 
 */
public class ScalarMapModifier implements NestingMapModifier {

  /**
   * The nested modifier which is actually used to modify the action.
   */
  protected MyoMapModifier nestedMod;

  /**
   * The value that the nested modifier's output will be multiplied by.
   */
  protected double scalarValue;

  /**
   * Constructor.
   * @param mod The modifier who's output will be multiplied.
   * @param scalar The amount mod's output will be multiplied by.
   */
  public ScalarMapModifier(MyoMapModifier mod, double scalar) {
    nestedMod = mod;
    scalarValue = scalar;
  }

  /**
   * Modifies the actionScale using the nested modifier, multiplying the output by scalarValue.
   * @param actionScale The current scale of the action.
   * @param delta How long since the last time this ws called.
   * @return The modified actionScale.
   */
  public double modifyAction(double actionScale, long delta) {
    return nestedMod.modifyAction(actionScale, delta) * scalarValue;
  }

  /**
   * Accessor for the modifier nested within.
   * @return The modifier nested within this object.
   */
  public MyoMapModifier getNestedModifier() {
    return nestedMod;
  }

}

