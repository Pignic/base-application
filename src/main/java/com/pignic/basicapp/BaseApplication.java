package com.pignic.basicapp;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BaseApplication extends JFrame {

	public static abstract class Engine {

		protected BaseApplication container;

		public BaseApplication getContainer() {
			return container;
		}

		public abstract void init();

		public abstract void render(Graphics2D g);

		public abstract void update(long ms);
	}

	private static final long serialVersionUID = 3371039102546153786L;

	private boolean allowDrag = true;

	private final Vector2D center = new Vector2D();

	private final Engine engine;

	private int fps = 0;

	private boolean isFullScreen = false;

	private final MouseHandler mouseHandler;

	private float scale = 1;

	private final JPanel scene;

	private float zoomLevel = 1f;

	public BaseApplication(final int width, final int height, final Engine engine) {
		this.engine = engine;
		engine.container = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		mouseHandler = new MouseHandler(this);
		addMouseListener(mouseHandler);
		addMouseWheelListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
		scene = new JPanel() {

			private static final long serialVersionUID = -2794503265814939228L;

			@Override
			public void paintComponent(final Graphics g) {
				super.paintComponent(g);
				final Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				engine.render(g2d);
				g2d.dispose();
			}
		};
		setSize(width, height);
		add(scene);
		center.set(scene.getWidth() / 2f, scene.getHeight() / 2f);
		engine.init();
		setVisible(true);
	}

	public Vector2D getCenter() {
		return center;
	}

	public Vector2D getCoordinateToScreen(final double x, final double y) {
		final Vector2D coordinates = new Vector2D();
		coordinates.x = (x - center.x) * zoomLevel * scale + scene.getWidth() / 2f;
		coordinates.y = (y - center.y) * zoomLevel * scale + scene.getHeight() / 2f;
		return coordinates;
	}

	public Vector2D getCoordinateToScreen(final Vector2D point) {
		return getCoordinateToScreen(point.x, point.y);
	}

	public int getFps() {
		return fps;
	}

	public float getScale() {
		return scale;
	}

	public JPanel getScene() {
		return scene;
	}

	public Vector2D getScreenToCoordinate(final double x, final double y) {
		final Vector2D screen = new Vector2D();
		screen.x = x / zoomLevel + center.x - scene.getWidth() / (2 * zoomLevel * scale);
		screen.y = y / zoomLevel + center.y - scene.getHeight() / (2 * zoomLevel * scale);
		return screen;
	}

	public Vector2D getScreenToCoordinate(final Vector2D point) {
		return getScreenToCoordinate(point.x, point.y);
	}

	public float getZoomLevel() {
		return zoomLevel;
	}

	public boolean isAllowDrag() {
		return allowDrag;
	}

	public void setAllowDrag(final boolean allowDrag) {
		this.allowDrag = allowDrag;
	}

	public void setScale(final float scale) {
		this.scale = scale;
	}

	public void setZoomLevel(final float zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public void startMainLoop(final int desiredFPS) {
		final long updateTick = (long) (1000f / desiredFPS);
		final BaseApplication instance = this;
		final Timer timer = new Timer((int) updateTick, new ActionListener() {
			private long lastFpsTime = 0;
			private long lastLoopTime = System.currentTimeMillis();
			private long now;
			private int updateCount = 0;
			private long updateLength;

			@Override
			public void actionPerformed(final ActionEvent e) {
				now = e.getWhen();
				updateLength = now - lastLoopTime;
				lastLoopTime = now;
				lastFpsTime += updateLength;
				++updateCount;
				if (lastFpsTime >= 1000) {
					instance.fps = updateCount;
					lastFpsTime = 0;
					updateCount = 0;
				}
				engine.update(updateLength);
				SwingUtilities.invokeLater(() -> repaint());
			}
		});
		timer.start();
	}

	public boolean toggleFullSreen() {
		final GraphicsDevice device = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		isFullScreen = !isFullScreen;
		dispose();
		setUndecorated(isFullScreen);
		setResizable(!isFullScreen);
		device.setFullScreenWindow(isFullScreen ? this : null);
		setVisible(true);
		validate();
		return isFullScreen;
	}

	public void zoomIn() {
		zoomLevel *= 1f + 0.1f
		// / scale
		;
	}

	public void zoomOut() {
		zoomLevel *= 1f - 0.1f
		// / scale
		;
	}
}
