package myo2key;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;

// http://docs.oracle.com/javase/7/docs/api/java/awt/Robot.html
public class RobotController extends Robot {
  private static RobotController ROBOT_CONTROLLER = null;

  public static RobotController getInstance() {
    if(ROBOT_CONTROLLER == null) {
      try {
        ROBOT_CONTROLLER = new RobotController();
      } catch(AWTException awte) {
        awte.printStackTrace();
      }
    }
    return ROBOT_CONTROLLER;
  }

  private RobotController() throws AWTException {
  }

  public void mouseDeltaMove(int dx, int dy) {
    Point mouseLoc = getMouseLocation();
    int x = mouseLoc.x;
    int y = mouseLoc.y;
    x += dx;
    y += dy;
    super.mouseMove(x, y);
  }

  private Point getMouseLocation() {
    return MouseInfo.getPointerInfo().getLocation();
  }
}
