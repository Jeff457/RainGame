package com.jeffstanton.rain.graphics;

import java.util.Random;

public class Screen {
	
	private int WIDTH, HEIGHT;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] pixels;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	private Random random = new Random();
	
	public Screen(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];  // 1 int for each pixel in screen
		
		// Generates random colors
		for (int i = 0; i < (MAP_SIZE * MAP_SIZE); i++){
			tiles[i] = random.nextInt(0xffffff);
		} // end for
	} // end constructor
	
	// clear the screen
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}  // end for
	} // end clear
	
	public void render(int xOffSet, int yOffSet) {
		/** Start at top left corner and fill to the right
		 *  Move down until the entire screen is filled  */
		for (int y = 0; y < HEIGHT; y++) {
			int yy = y + yOffSet;
			for (int x = 0; x < WIDTH; x++) {
				int xx = x + xOffSet;
				int tileIndex = ((xx / 16) & MAP_SIZE_MASK) + ((yy / 16) & MAP_SIZE_MASK) * MAP_SIZE;  // Want first 16px to be the same tile
				// Go down to desired row (y * WIDTH) then to desired column (+x)
				pixels[x + y * WIDTH] = tiles[tileIndex];  // index of current pixel we want to fill
			} // end inner for
		} // end outer for
	} // end render

} // end Screen
