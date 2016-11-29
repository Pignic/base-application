package com.pignic.basicapp;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseHandler extends MouseAdapter {

	private final BaseApplication application;

	private boolean isDragged = false;

	private final Vector2D previousMousePos = new Vector2D();

	public MouseHandler(final BaseApplication application) {
		this.application = application;
	}

	@Override
	public void mouseDragged(final MouseEvent event) {
		if ((event.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) {
			if (isDragged) {
				application.getCenter()
						.sub(new Vector2D((event.getX() - previousMousePos.x) / application.getZoomLevel(),
								(event.getY() - previousMousePos.y) / application.getZoomLevel()));
			}
		}
		if ((event.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) == InputEvent.BUTTON3_DOWN_MASK) {

		}
		isDragged = true;
		previousMousePos.set(event.getX(), event.getY());
		super.mouseDragged(event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(final MouseEvent event) {
		super.mousePressed(event);
	}

	@Override
	public void mouseReleased(final MouseEvent event) {
		if (!isDragged) {
			if (event.getButton() == 1) {

			} else if (event.getButton() == 3) {

			}
		} else {
			isDragged = false;
			if (event.getButton() == 3) {

			}
		}
		super.mouseReleased(event);
	};

	@Override
	public void mouseWheelMoved(final MouseWheelEvent event) {
		if (event.getWheelRotation() > 0) {
			application.zoomOut();
		} else {
			application.zoomIn();
		}
		super.mouseWheelMoved(event);
	}
}
