package com.jeffstanton.rain.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private String path;  // Contains path to sprite sheet
	private final int SIZE;  // Size of the sprite sheet
	public int[] pixels;
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE * SIZE];  // set pixel to size of sprite sheet
	} // end constructor
	
	private void load() {
		// Create a new image and set it to the image provided by the path
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int width = image.getWidth();
			int height = image.getHeight();
			image.getRGB(0, 0, width, height, pixels, 0, width);  // translate loaded image to pixels
		} catch (IOException e) {
			e.printStackTrace();
		} // end try... catch
	} // end load
} // end SpriteSheet
