package uet.oop.bomberman.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean[] booleanKeys = new boolean[120];
	public boolean UP, DOWN, LEFT, RIGHT, BOMB, pause, resume, restart, end;

	public void update() {
		UP = booleanKeys[KeyEvent.VK_UP] || booleanKeys[KeyEvent.VK_W];
		DOWN = booleanKeys[KeyEvent.VK_DOWN] || booleanKeys[KeyEvent.VK_S];
		LEFT = booleanKeys[KeyEvent.VK_LEFT] || booleanKeys[KeyEvent.VK_A];
		RIGHT = booleanKeys[KeyEvent.VK_RIGHT] || booleanKeys[KeyEvent.VK_D];
		BOMB = booleanKeys[KeyEvent.VK_SPACE];
		pause = booleanKeys[KeyEvent.VK_P];
		resume = booleanKeys[KeyEvent.VK_O];
		restart = booleanKeys[KeyEvent.VK_R];
		end = booleanKeys[KeyEvent.VK_N];
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		booleanKeys[e.getKeyCode()] = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		booleanKeys[e.getKeyCode()] = false;
	}

}
