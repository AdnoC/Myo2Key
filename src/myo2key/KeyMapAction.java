package myo2key;

/**
 * An action that presses keys when the mapping is activated.
 * @author Adam Cutler
 */
public class KeyMapAction implements MyoMapAction {

  // The code of the key to press.
  private final int keycode;
  // The code of keys to press while this is also being pressed.
  private final int[] modifiers;

  /**
   * Constructs a KeyMapAction with the specified keycode and optional modifiers.
   * @param code The keycode of the key to press.
   * @param mods A list of keycodes for modifier keys such as shift of ctrl.
   */
  public KeyMapAction(int code, int... mods) {
    keycode = code;
    modifiers = mods;
  }

  /**
   * Presses a key while holding down modifiers, pressing the key scale number of times.
   * @param scale The number of times the key should be pressed.
   */
  public void fireAction(double scale) {
    RobotController rc = RobotController.getRobotController();
    if(modifiers.length > 0) {
      for(int mod : modifiers) {
        rc.keyPress(mod);
      }
    }

    for(int i = (int) scale; i > 0; i--) {
      rc.keyPress(keycode);
      rc.keyRelease(keycode);
    }

    if(modifiers.length > 0) {
      for(int mod : modifiers) {
        rc.keyRelease(mod);
      }
    }
  }
}
