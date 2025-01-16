package org.pwrup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;
import org.pwrup.util.Vec2;

public class Vec2Test {

  private static final double EPSILON = 1e-10;

  @Test
  public void testVec2Construction() {
    Vec2 vec = new Vec2(3.0, 4.0);
    assertThat(vec.getX()).isEqualTo(3.0);
    assertThat(vec.getY()).isEqualTo(4.0);

    assertThat(Vec2.ZERO.getX()).isEqualTo(0.0);
    assertThat(Vec2.ZERO.getY()).isEqualTo(0.0);
  }

  @Test
  public void testFromPolar() {
    Vec2 vec = Vec2.fromPolar(5.0, Math.PI / 4);
    assertThat(vec.getX()).isCloseTo(5.0 / Math.sqrt(2), within(EPSILON));
    assertThat(vec.getY()).isCloseTo(5.0 / Math.sqrt(2), within(EPSILON));
  }

  @Test
  public void testVec2Modulo() {
    Vec2 vec = new Vec2(3.0, 4.0);
    assertThat(vec.getModulo()).isEqualTo(5.0);
  }

  @Test
  public void testVec2Angle() {
    Vec2 vec = new Vec2(1.0, 1.0);
    assertThat(vec.getAngle()).isCloseTo(Math.PI / 4, within(EPSILON));

    vec = new Vec2(0.0, 1.0);
    assertThat(vec.getAngle()).isCloseTo(Math.PI / 2, within(EPSILON));

    vec = new Vec2(-1.0, 0.0);
    assertThat(vec.getAngle()).isCloseTo(Math.PI, within(EPSILON));
  }

  @Test
  public void testScaleToModulo() {
    Vec2 vec = new Vec2(10.0, 0.0);
    Vec2 scaled = vec.scaleToModulo(1.0);
    assertThat(scaled.getX()).isEqualTo(1.0);
    assertThat(scaled.getY()).isEqualTo(0.0);

    vec = new Vec2(3.0, 4.0);
    scaled = vec.scaleToModulo(10.0);
    assertThat(scaled.getX()).isEqualTo(6.0);
    assertThat(scaled.getY()).isEqualTo(8.0);
  }

  @Test
  public void testWrapInto1() {
    Vec2 vec = new Vec2(10.0, 0.0);
    Vec2 wrapped = vec.desaturate(30.0);
    assertThat(wrapped.getX()).isCloseTo(1.0 / 3.0, within(EPSILON));
    assertThat(wrapped.getY()).isEqualTo(0.0);
  }

  @Test
  public void testWrapInto1Self() {
    Vec2 vec = new Vec2(10.0, 0.0);
    vec.wrapInto1Self(30.0);
    assertThat(vec.getX()).isCloseTo(1.0 / 3.0, within(EPSILON));
    assertThat(vec.getY()).isEqualTo(0.0);
  }

  @Test
  public void testVec2Addition() {
    Vec2 vec1 = new Vec2(1.0, 2.0);
    Vec2 vec2 = new Vec2(3.0, 4.0);
    Vec2 sum = vec1.add(vec2);

    assertThat(sum.getX()).isEqualTo(4.0);
    assertThat(sum.getY()).isEqualTo(6.0);
  }

  @Test
  public void testVec2Subtraction() {
    Vec2 vec1 = new Vec2(3.0, 4.0);
    Vec2 vec2 = new Vec2(1.0, 2.0);
    Vec2 diff = vec1.subtract(vec2);

    assertThat(diff.getX()).isEqualTo(2.0);
    assertThat(diff.getY()).isEqualTo(2.0);
  }

  @Test
  public void testNormalize() {
    Vec2 vec = new Vec2(3.0, 4.0);
    Vec2 normalized = vec.normalize();
    assertThat(normalized.getX()).isCloseTo(0.6, within(EPSILON));
    assertThat(normalized.getY()).isCloseTo(0.8, within(EPSILON));
    assertThat(normalized.getModulo()).isCloseTo(1.0, within(EPSILON));
  }

  @Test
  public void testLargestModulo() {
    Vec2[] vectors = new Vec2[] {
      new Vec2(1.0, 0.0),
      new Vec2(3.0, 4.0),
      new Vec2(0.0, 2.0),
    };
    assertThat(Vec2.largestModulo(vectors)).isEqualTo(5.0);
  }
}
        assertThat(sum.getY()).isEqualTo(6.0);
    }

    @Test
    public void testVec2Subtraction() {
        Vec2 vec1 = new Vec2(3.0, 4.0);
        Vec2 vec2 = new Vec2(1.0, 2.0);
        Vec2 diff = vec1.subtract(vec2);

        assertThat(diff.getX()).isEqualTo(2.0);
        assertThat(diff.getY()).isEqualTo(2.0);
    }

    @Test
    public void testNormalize() {
        Vec2 vec = new Vec2(3.0, 4.0);
        Vec2 normalized = vec.normalize();
        assertThat(normalized.getX()).isCloseTo(0.6, within(EPSILON));
        assertThat(normalized.getY()).isCloseTo(0.8, within(EPSILON));
        assertThat(normalized.getModulo()).isCloseTo(1.0, within(EPSILON));
    }

    @Test
    public void testLargestModulo() {
        Vec2[] vectors = new Vec2[] {
                new Vec2(1.0, 0.0),
                new Vec2(3.0, 4.0),
                new Vec2(0.0, 2.0)
        };
        assertThat(Vec2.largestModulo(vectors)).isEqualTo(5.0);
    }
}
