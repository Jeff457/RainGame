package com.jeffstanton.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import com.jeffstanton.rain.graphics.Screen;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	// Create 16:9 window of width 300px and scaled to 900px
	public static int WIDTH = 300;
	public static int HEIGHT = WIDTH / 16 * 9;
	public static int SCALE = 3;
	public static String title = "Rain";
	
	private Thread gameThread;
	private JFrame frame;
	private boolean running = false;  // Indicate the game is running
	
	private Screen screen;
	
	// Holds final rendered image
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	// Get array of pixels that make up the image and get the data buffer that handles the raster
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	// Constructor
	public Game() {
		// Set size of window
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		
		// Create a new screen
		screen = new Screen(WIDTH, HEIGHT);
		
		// Create a new Window, center it, and make it visible
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	} // end Constructor
	
	// Start the game
	public synchronized void start() {
		running = true;
		gameThread = new Thread(this, "Game");  
		gameThread.start();  // Calls run method
	}  // end start
	
	// Stop the game
	public synchronized void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}  // end stop
	
	public void run() {
		// Upon starting game, retrieve computers current time
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();  // Set to 1 second
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;  // Counts how many frames are rendered per second
		int updates = 0;  // Counts how many times per second the update method is called (should be 60)
		while(running) {
			long now = System.nanoTime(); // System time has changed once this line is executed
			delta += (now - lastTime) / ns;
			lastTime = now;
			// Ensure update gets called 60 times per second
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			} // end inner while
			render();  // Display game graphics
			frames++;
			
			// Executes once per second to keep track of updates and frames per second
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// Display the title and FPS counter in the window
				frame.setTitle(title + "  |  " + frames + " FPS");
				// Reset updates and frames counter (only need them to keep track of executions per second)
				updates = 0;
				frames = 0;
			} // end if
			
		} // end outer while
		stop();  // end the game
	}  // end run
	
	public void update() {
		
	}  // end update
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {  // Create a BufferStrategy if it hasn't been done
			createBufferStrategy(3);  // Create 3 buffers
			return;  // Leave render
		}  // end if
		
		// Clear the screen, render the image, and then display the image
		screen.clear();
		screen.render();
		
		// Copy pixels
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		// Create graphics context for the buffer
		// Fill the entire window with the buffer image
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image,  0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}  // end render
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(game.title);
		game.frame.add(game);
		game.frame.pack();
		
		game.start();
	}  // end main
}  // end Game
