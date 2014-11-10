package myo2key;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.FirmwareVersion;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.enums.Arm;
import com.thalmic.myo.enums.PoseType;
import com.thalmic.myo.enums.VibrationType;
import com.thalmic.myo.enums.XDirection;

// A lot of this was taken from com.thalmic.myo.example.DataCollector
public class DeviceDataStorage implements DeviceListener {
  // Is this important?
  private static final int SCALE = 18;
  private double roll;
  private double pitch;
  private double yaw;
  private Vector3 gyroscopeData;
  private Vector3 acceleration;
  private Pose currentPose;
  private Arm whichArm;
  private long poseTimestamp;
  private long orientationTimestamp;
  private long gyroscopeDataTimestamp;
  private long accelerationTimestamp;
  // TODO: Figure out what tells you what.

  public DeviceDataStorage() {
    roll = 0;
    pitch = 0;
    yaw = 0;
    currentPose = new Pose();
    gyroscopeData = new Vector3();
    acceleration = new Vector3();
  }

  public Vector3 getgyroscopeData() {
    return gyroscopeData;
  }

  public Vector3 getAcceleration() {
    return acceleration;
  }

  public double getRoll() {
    return roll;
  }

  public double getPitch() {
    return pitch;
  }

  public double getYaw() {
    return yaw;
  }

  public boolean hasPose() {
    return currentPose.getType() != PoseType.REST;
  }

  public Pose getPose() {
    return currentPose;
  }

  public boolean hasArm() {
    return whichArm != null;
  }

  public Arm getArm() {
    return whichArm;
  }

  @Override
  public void onGyroscopeData(Myo myo, long timestamp, Vector3 gyro) {
    if(!gyroscopeData.equals(gyro)) {
      gyroscopeDataTimestamp = timestamp;
      gyroscopeData = new Vector3(gyro);
    }
  }

  @Override
  public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
    Quaternion normalized = rotation.normalized();

    double rollT = Math.atan2(2.0f * (normalized.getW() * normalized.getX() + normalized.getY() * normalized.getZ()), 1.0f - 2.0f * (normalized.getX() * normalized.getX() + normalized.getY() * normalized.getY()));
    double pitchT = Math.asin(2.0f * (normalized.getW() * normalized.getY() - normalized.getZ() * normalized.getX()));
    double yawT = Math.atan2(2.0f * (normalized.getW() * normalized.getZ() + normalized.getX() * normalized.getY()), 1.0f - 2.0f * (normalized.getY() * normalized.getY() + normalized.getZ() * normalized.getZ()));

    double newRoll = ((rollT + Math.PI) / (Math.PI * 2.0) * SCALE);
    double newPitch = ((pitchT + Math.PI / 2.0) / Math.PI * SCALE);
    double newYaw = ((yawT + Math.PI) / (Math.PI * 2.0) * SCALE);

    if(newRoll != roll || newPitch != pitch || newYaw != yaw) {
      orientationTimestamp = timestamp;
      roll = newRoll;
      pitch = newPitch;
      yaw = newYaw;
    }
  }

  @Override
  public void onPose(Myo myo, long timestamp, Pose pose) {
    if(!currentPose.equals(pose)) {
      poseTimestamp = timestamp;
      currentPose = pose;
    }
  }

  @Override
  public void onArmRecognized(Myo myo, long timestamp, Arm arm, XDirection xDirection) {
    whichArm = arm;
  }

  @Override
  public void onArmLost(Myo myo, long timestamp) {
    whichArm = null;
  }

  @Override
  public void onAccelerometerData(Myo myo, long timestamp, Vector3 accel) {
    if(!acceleration.equals(accel)) {
      accelerationTimestamp = timestamp;
      acceleration = new Vector3(accel);
    }
  }

  @Override
  public void onConnect(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
  }

  @Override
  public void onDisconnect(Myo myo, long timestamp) {
  }

  @Override
  public void onPair(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
  }

  @Override
  public void onUnpair(Myo myo, long timestamp) {
  }

  @Override
  public void onRssi(Myo myo, long timestamp, int rssi) {
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("\r");

    String rollD = String.format("[%s%s]", repeatCharacter('*', (int) roll), repeatCharacter(' ', (int) (SCALE - roll)));
    String pitchD = String.format("[%s%s]", repeatCharacter('*', (int) pitch), repeatCharacter(' ', (int) (SCALE - pitch)));
    String yawD = String.format("[%s%s]", repeatCharacter('*', (int) yaw), repeatCharacter(' ', (int) (SCALE - yaw)));

    String poseString = null;
    if (whichArm != null) {
        String poseTypeString = currentPose.getType()
          .toString();
        poseString = String.format("[%s][%s%" + (SCALE - poseTypeString.length()) + "s]", whichArm == Arm.ARM_LEFT ? "L" : "R", poseTypeString, " ");
    } else {
        poseString = String.format("[?][%14s]", " ");
    }

    String accelX = String.format("%.3f", acceleration.getX());
    String accelY = String.format("%.3f", acceleration.getY());
    String accelZ = String.format("%.3f", acceleration.getZ());
    String accelString = "[" + accelX + ", " + accelY + ", " + accelZ + "] ";
    String gyroX = String.format("%.3f", gyroscopeData.getX());
    String gyroY = String.format("%.3f", gyroscopeData.getY());
    String gyroZ = String.format("%.3f", gyroscopeData.getZ());
    String gyroString = "[" + gyroX + ", " + gyroY + ", " + gyroZ + "] ";

    builder.append(rollD);
    builder.append(pitchD);
    builder.append(yawD);
    builder.append(poseString);
    builder.append(accelString);
    builder.append(gyroString);
    return builder.toString();
  }

  private String repeatCharacter(char character, int numOfTimes) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < numOfTimes; i++) {
        builder.append(character);
    }
    return builder.toString();
  }

}
