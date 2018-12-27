package ru.stqa.pft.sandbox;


import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceTest {

  @Test
  public void testDistance() {
    Point p3 = new Point(10, 40);
    Point p4 = new Point(20,10);

    Assert.assertEquals(MyFirstProgram.distance(p3, p4),31.622776601683793);

    Assert.assertEquals(p3.distance(p4), 31.622776601683793);
  }
}
