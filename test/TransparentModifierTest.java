import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import myo2key.TransparentMapModifier;

public class TransparentModifierTest {

  @Test
  public void transparentModTest() {
    TransparentMapModifier tmm = new TransparentMapModifier();
    final double a = .12349;
    final double b = .011565;
    final double c = 58610.2;
    final double epsilon = .01;
    assertEquals(tmm.modifyAction(a, 1598L), a, epsilon);
    assertEquals(tmm.modifyAction(b, 962L), b, epsilon);
    assertEquals(tmm.modifyAction(c, 1000000L), c, epsilon);

  }

}
