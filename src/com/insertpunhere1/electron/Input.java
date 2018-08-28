package com.insertpunhere1.electron;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	boolean[] keys = new boolean[255];
	
	int[] keyPresses = new int[255];
	
	public Input() {
		for(int index = 0; index < 255; index++) {
			keys[index] = false;
			
			keyPresses[index] = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
		keyPresses[e.getKeyCode()]++;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		keys[e.getKeyCode()] = false;
	}
	
	public boolean getKey(int keyCode) {
		return keys[keyCode];
	}
	
	public boolean getKey(char keyChar) {
		return keys[keyChar];
	}
	
	public int getNumberOfPresses(int keyCode) {
		return keyPresses[keyCode];
	}
	
	public int getNumberOfPresses(char keyChar) {
		return keyPresses[keyChar];
	}

	public boolean[] getKeys() {
		return keys;
	}

	public void setKeys(boolean[] keys) {
		this.keys = keys;
	}

	public int[] getKeyPresses() {
		return keyPresses;
	}

	public void setKeyPresses(int[] keyPresses) {
		this.keyPresses = keyPresses;
	}
}
