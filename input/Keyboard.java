package com.jeffstanton.rain.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	// Each key on the keyboard has 1 of 2 states: 
	// (1 - true) pressed or (2 - false) released
	private boolean[] keys = new boolean[107];
	public boolean up, down, left, right;  // Keep track of corresponding key
	
	// Checks to see if a particular key is pressed
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
	} // end update

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;  // Set key to true if pressed
	} // end keyPressed

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;  // Set key to false if released
	} // end keyReleased

	public void keyTyped(KeyEvent e) {
		
	} // end keyTyped

} // end Keyboard
