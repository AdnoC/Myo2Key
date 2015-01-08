package myo2key;
import java.awt.event.InputEvent;

/**
 * An action to click mouse buttons.
 *
 * @author Adam Cutler
 * @version 
 */
public class MouseClickMapAction implements MyoMapAction {

  /**
   * The mask of the button to be pressed
   */
  protected int buttonMask;

  /**
   * Constructor for when the button mask is already known.
   */
  protected MouseClickMapAction() {

  }

  public MouseClickMapAction(int button) throws IllegalArgumentException {
    buttonMask = InputEvent.getMaskForButton(button);
  }

  /**
   * Clicks a mouse button.
   * @param scale Says whether or not to click.
   */
  public void fireAction(double scale) {
    if(scale != 0) {
      RobotController rc = RobotController.getInstance();
      rc.mousePress(buttonMask);
      rc.mouseRelease(buttonMask);
    }
  }
}

