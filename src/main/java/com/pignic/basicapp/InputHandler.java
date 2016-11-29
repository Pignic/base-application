package com.pignic.basicapp;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class InputHandler extends KeyAdapter {

	public static abstract class Action implements Callable<Object> {

		private boolean pressed;

		public Action() {

		}

		@Override
		public abstract Object call() throws Exception;

		protected boolean isPressed() {
			return pressed;
		}

		private void setPressed(final boolean pressed) {
			this.pressed = pressed;
		}
	}

	public static class Binding {

		private final Action action;
		private final char key;

		public Binding(final char key, final Action action) {
			this.key = key;
			this.action = action;
		}
	}

	private final Map<Character, Action> inputMap = new HashMap<Character, Action>();

	public InputHandler() {
	}

	public InputHandler(final Binding... bindings) {
		addBinding(bindings);
	}

	public InputHandler addBinding(final Binding... bindings) {
		for (final Binding binding : bindings) {
			inputMap.put(binding.key, binding.action);
		}
		return this;
	}

	public InputHandler addBinding(final char key, final Action action) {
		inputMap.put(key, action);
		return this;
	}

	public Action getAction(final char key) {
		return inputMap.get(key);
	}

	public boolean isDown(final char key) {
		final Action action = getAction(key);
		return action != null ? action.pressed : false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyPressed(final KeyEvent event) {
		final Action action = inputMap.get(new Character(event.getKeyChar()));
		if (action != null) {
			action.setPressed(true);
			try {
				action.call();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		super.keyPressed(event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyReleased(final KeyEvent event) {
		final Action action = inputMap.get(new Character(event.getKeyChar()));
		if (action != null) {
			action.setPressed(false);
			try {
				action.call();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public InputHandler removeBinding(final Binding binding) {
		return this.removeBinding(binding.key);
	}

	public InputHandler removeBinding(final char key) {
		inputMap.remove(key);
		return this;
	}

}
