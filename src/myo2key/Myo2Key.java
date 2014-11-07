package myo2key;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.VibrationType;
public class Myo2Key {
  public static void main(String[] args) {
    try {
      RobotController rc = RobotController.getRobotController();
      rc.mouseDeltaMove(50, 100);
      Hub hub = new Hub();
      System.out.println("Attempting to find a Myo.");
      Myo myo = hub.waitForMyo(10000);

      if(myo == null)
          throw new RuntimeException("Unable to find a Myo.");
      System.out.println("Connected to a Myo");
      myo.vibrate(VibrationType.VIBRATION_SHORT);

      DeviceDataStorage dataStorage = new DeviceDataStorage();
      hub.addListener(dataStorage);
      while(true) {
        hub.run(1000 / 20);
        System.out.println(dataStorage);
      }

    } catch(Exception e) {
      System.err.println("Error: ");
      e.printStackTrace();
      System.exit(1);
    }
  }
}
