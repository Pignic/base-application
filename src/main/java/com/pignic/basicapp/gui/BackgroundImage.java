package com.pignic.basicapp.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class BackgroundImage extends JComponent {

	public static enum Constraints {
		BOTTOM, BOTTOMLEFT, BOTTOMRIGHT, CENTER, CONTAIN, COVER, LEFT, RIGHT, TOP, TOPLEFT, TOPRIGHT
	}

	private static final long serialVersionUID = 4493973577600915081L;
	private Constraints constraints = Constraints.CENTER;
	private BufferedImage image;
	private int margin = 0;
	private float scale = 1;
	private TexturePaint texturePaint;

	public BackgroundImage(final BufferedImage texture) {
		setTexture(texture);
		image = null;
	}

	public BackgroundImage(final BufferedImage texture, final BufferedImage image) {
		this(texture);
		this.image = image;
	}

	public Constraints getConstraints() {
		return constraints;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getMargin() {
		return margin;
	}

	public float getScale() {
		return scale;
	}

	public TexturePaint getTexturePaint() {
		return texturePaint;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(texturePaint);
		g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));
		if (image != null) {
			final AffineTransform at = new AffineTransform();
			float x = 0;
			float y = 0;
			float scale = this.scale;
			final float screenRatio = getWidth() / (float) getHeight();
			if (constraints == Constraints.CONTAIN) {
				scale = screenRatio < 1 ? (getWidth() - margin * 2) / (float) image.getWidth()
						: (getHeight() - margin * 2) / (float) image.getHeight();
			} else if (constraints == Constraints.COVER) {
				scale = screenRatio > 1 ? (getWidth() - margin * 2) / (float) image.getWidth()
						: (getHeight() - margin * 2) / (float) image.getHeight();
			}
			if (constraints == Constraints.BOTTOMLEFT || constraints == Constraints.LEFT
					|| constraints == Constraints.TOPLEFT) {
				x = margin;
			} else if (constraints == Constraints.BOTTOM || constraints == Constraints.CENTER
					|| constraints == Constraints.TOP || constraints == Constraints.CONTAIN
					|| constraints == Constraints.COVER) {
				x = getWidth() / 2f - image.getWidth() * scale / 2f;
			} else {
				x = getWidth() - image.getWidth() * scale - margin;
			}
			if (constraints == Constraints.TOPLEFT || constraints == Constraints.TOP
					|| constraints == Constraints.TOPRIGHT) {
				y = margin;
			} else if (constraints == Constraints.LEFT || constraints == Constraints.CENTER
					|| constraints == Constraints.RIGHT || constraints == Constraints.CONTAIN
					|| constraints == Constraints.COVER) {
				y = getHeight() / 2f - image.getHeight() * scale / 2f;
			} else {
				y = getHeight() - image.getHeight() * scale;
			}
			at.translate(x, y);
			at.scale(scale, scale);
			g2d.drawImage(image, at, null);
		}
		g2d.dispose();
		super.paintComponent(g);
	}

	public void setConstraints(final Constraints constraints) {
		this.constraints = constraints;
	}

	public void setImage(final BufferedImage image) {
		this.image = image;
	}

	public void setMargin(final int margin) {
		this.margin = margin;
	}

	public void setScale(final float scale) {
		this.scale = scale;
	}

	public void setTexture(final BufferedImage texture) {
		texturePaint = new TexturePaint(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()));
	}
}
