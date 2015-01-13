package myo2key;

/**
 * An interface for all MyoMapModifiers that have other modifiers nested inside them.
 *
 * @author Adam Cutler
 * @version 
 */
public interface NestingMapModifier extends MyoMapModifier {

  /**
   * Accessor for the modifier nested within.
   * @return The modifier nested within this object.
   */
  public MyoMapModifier getNestedModifier();

}
