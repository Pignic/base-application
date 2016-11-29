package com.pignic.basicapp;

public class MathUtil {

	/**
	 * Get the angle (in rad) between 3 points
	 *
	 * @param center The center of the angle
	 * @param next the first edge
	 * @param previous the second edge
	 * @return the angle
	 */
	public static float getAngle(final Vector2D center, final Vector2D next, final Vector2D previous) {
		float angle = (float) (Math.atan2(next.y - center.y, next.x - center.x)
				- Math.atan2(previous.y - center.y, previous.x - center.x));
		if (angle >= Math.PI) {
			angle -= 2 * Math.PI;
		} else if (angle <= -Math.PI) {
			angle += 2 * Math.PI;
		}
		return angle;
	}

	/**
	 * Get the closest point from a point to a line
	 *
	 * @param a the first point in the line
	 * @param b the second point in the line
	 * @param test the point to test
	 * @return a point on the line (can be out of the segment [a, b]) closest to the test point.
	 */
	public static Vector2D getClosestPoint(final Vector2D a, final Vector2D b, final Vector2D test) {
		final double testAngle = getAngle(b, a, new Vector2D(0, b.y)) + Math.PI / 2f;
		final Vector2D testb = new Vector2D(Math.round(test.x + Math.cos(testAngle) * 100f),
				Math.round(test.y + Math.sin(testAngle) * 100f));
		final double x = ((b.x - a.x) * (test.x * testb.y - testb.x * test.y)
				- (testb.x - test.x) * (a.x * b.y - b.x * a.y))
				/ ((a.x - b.x) * (test.y - testb.y) - (a.y - b.y) * (test.x - testb.x));
		final double y = ((test.y - testb.y) * (a.x * b.y - b.x * a.y)
				- (a.y - b.y) * (test.x * testb.y - testb.x * test.y))
				/ ((a.x - b.x) * (test.y - testb.y) - (a.y - b.y) * (test.x - testb.x));
		return new Vector2D(x, y);
	}

	/**
	 * Fast invert square root.
	 *
	 * @param val the value.
	 * @return the inverted square root.
	 */
	public static double invSqrt(double val) {
		final double xhalf = 0.5d * val;
		long i = Double.doubleToLongBits(val);
		i = 0x5fe6ec85e7de30daL - (i >> 1);
		val = Double.longBitsToDouble(i);
		val = val * (1.5d - xhalf * val * val);
		return val;
	}
}
