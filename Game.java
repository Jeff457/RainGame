package com.jeffstanton.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	// Create 16:9 window of width 300px and scaled to 900px
	public static int WIDTH = 300;
	public static int HEIGHT = WIDTH / 16 * 9;
	public static int SCALE = 3;
	
	private Thread gameThread;
	private JFrame frame;
	private boolean running = false;  // Indicate the game is running
	
	// Holds final rendered image
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	// Get array of pixels that make up the image and get the data buffer that handles the raster
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	// Constructor
	public Game() {
		// Set size of window
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		
		// Create a new Window, center it, and make it visible
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	// Start the game
	public synchronized void start() {
		running = true;
		gameThread = new Thread(this, "Game");  
		gameThread.start();  // Calls run method
	}
	
	// Stop the game
	public synchronized void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(running) {
			update();
			render();  // Display game graphics
		}
	}
	
	public void update() {
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {  // Create a BufferStrategy if it hasn't been done
			createBufferStrategy(3);  // Create 3 buffers
			return;  // Leave render
		}
		
		// Create graphics context for the buffer
		// Fill the entire window with the buffer image
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Rain");
		game.frame.add(game);
		game.frame.pack();
		
		game.start();
	}
}
