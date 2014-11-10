package myo2key;

/**
 * Interface for the second half of the mapping; Causes actions to occur on the computer
 * such as moving the mouse or typing keys.
 * @author Adam Cutler
 */
public interface MyoMapAction {
  /**
   * Fires the action, and modifies its behavior based on scale.
   * @param scale Modifies to what degree the action is carried out.
   */
  public void ffireAction(double scale);
}
