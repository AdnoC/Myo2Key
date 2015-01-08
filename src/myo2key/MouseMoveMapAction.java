package myo2key;

/**
 * An action to move the mouse.
 *
 * @author Adam Cutler
 * @version 
 */
public class MouseMoveMapAction implements MyoMapAction {

  /**
   * The sensativity to use when moving the mouse horizonally.
   * Set to 0 to not move horizontally.
   */
  protected int xSensativity;
  /**
   * The sensativity to use when moving the mouse vertically.
   * Set to 0 to not move vertically.
   */
  protected int ySensativity;

  /**
   * Constructor.
   * @param xSens The X-Sensativity to use.
   * @param ySens The X-Sensativity to use.
   */
  public MouseMoveMapAction(int xSens, int ySens) {
    xSensativity = xSens;
    ySensativity = ySens;
  }

  /**
   * Moves the mouse a distance modified by the scale.
   * @param scale Modifies how far you move the mouse.
   */
  public void fireAction(double scale) {
    int x = (int)(scale * xSensativity);
    int y = (int)(scale * ySensativity);

    RobotController rc = RobotController.getInstance();
    rc.mouseDeltaMove(x,y);
  }
}
