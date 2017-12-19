package com.pignic.basicapp;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.border.LineBorder;

import com.pignic.basicapp.BaseApplication.Engine;
import com.pignic.basicapp.InputHandler.Action;
import com.pignic.basicapp.InputHandler.Binding;

public class BaseApplicationTest extends Engine {

	public static void main(final String[] args) {
		new BaseApplication(800, 600, new BaseApplicationTest()).startMainLoop(60);
	}

	public BaseApplicationTest() {

	}

	@Override
	public void init() {
		container.getScene().setBorder(new LineBorder(Color.RED));
		container.addKeyListener(new InputHandler(new Binding('f', new Action() {
			@Override
			public final Object call() throws Exception {
				if (isPressed()) {
					return container.toggleFullSreen();
				}
				return null;
			}
		}), new Binding('', new Action() {
			@Override
			public final Object call() throws Exception {
				if (isPressed()) {
					System.exit(0);
				}
				return null;
			}
		})));
	}

	@Override
	public void render(final Graphics2D g) {
		g.setColor(Color.RED);
		g.drawString("FPS: " + Integer.toString(container.getFps()), 0, 10);
	}

	@Override
	public void update(final long ms) {
		// TODO Auto-generated method stub
	}

}
