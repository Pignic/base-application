package com.pignic.basicapp;

public class Vector2D {

	public double x;

	public double y;

	public Vector2D() {
		x = 0;
		y = 0;
	}

	public Vector2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(final Vector2D copy) {
		x = copy.x;
		y = copy.y;
	}

	public Vector2D add(final double x, final double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2D add(final Vector2D other) {
		x += other.x;
		y += other.y;
		return this;
	}

	public Vector2D calcScalarMult(final double scalar) {
		return new Vector2D(x, y).scalarMult(scalar);
	}

	public Vector2D calcSub(final Vector2D other) {
		return new Vector2D(x, y).sub(other);
	}

	public double distance(final Vector2D other) {
		return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2));
	}

	public double getAngle() {
		return Math.atan2(y, x);
	}

	public double getLength() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector2D getNormalized() {
		return new Vector2D(x, y).normalize();
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	public Vector2D negate() {
		x *= -1;
		y *= -1;
		return this;
	}

	public Vector2D normalize() {
		return scalarMult(MathUtil.invSqrt(x * x + y * y));
	}

	public Vector2D scalarMult(final double scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public Vector2D set(final double x, final double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2D set(final Vector2D other) {
		x = other.x;
		y = other.y;
		return this;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(final double x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(final double y) {
		this.y = y;
	}

	public Vector2D sub(final double x, final double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2D sub(final Vector2D other) {
		x -= other.x;
		y -= other.y;
		return this;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}
}
