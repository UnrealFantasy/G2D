/**
 * 
 */
package com.insertpunhere1.electron;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

/**
 * @author Dexter Burk
 *
 */
public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 200;
	public static final int HEIGHT = 100;

	public static final int SCALE_X = 6;
	public static final int SCALE_Y = 6;

	public static final String TITLE = "Game";

	public boolean running = false;

	private JFrame frame;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private Sheet sheet;

	private Screen screen;

	private Input input;

	private int[] colors = new int[216];

	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE_X, HEIGHT * SCALE_Y));
		setMaximumSize(new Dimension(WIDTH * SCALE_X, HEIGHT * SCALE_Y));
		setPreferredSize(new Dimension(WIDTH * SCALE_X, HEIGHT * SCALE_Y));

		frame = new JFrame(TITLE);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);

		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
	}

	public void initialize() {
		int index = 0;

		for (int red = 0; red < 6; red++) {
			for (int green = 0; green < 6; green++) {
				for (int blue = 0; blue < 6; blue++) {
					int r = (red * 255 / 5);
					int g = (green * 255 / 5);
					int b = (blue * 255 / 5);

					colors[index++] = r << 16 | g << 8 | b;
				}
			}
		}

		sheet = new Sheet("/levelSheet.png");

		screen = new Screen(sheet, WIDTH, HEIGHT);

		input = new Input();

		frame.addKeyListener(input);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		long lastTime = System.nanoTime();

		double nsPerFrame = 1000000000.0 / 60.0;
		double nsPerUpdate = 1000000000.0 / 60.0;

		int frames = 0, ticks = 0;

		long lastTimer = System.currentTimeMillis();

		double deltaTick = 0;
		double deltaFrame = 0;

		initialize();

		while (running) {
			long now = System.nanoTime();

			deltaTick += (now - lastTime) / nsPerUpdate;
			deltaFrame += (now - lastTime) / nsPerFrame;

			lastTime = now;

			while (deltaTick >= 1) {
				ticks++;

				tick();

				deltaTick--;
			}

			while (deltaFrame >= 1) {
				frames++;

				render();

				deltaFrame--;
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;

				System.out.println("FPS: " + frames + ", UPS (TPS): " + ticks);

				frames = ticks = 0;
			}
		}
	}

	public synchronized void start() {
		running = true;

		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	public void tick() {
		if (input.getKey(KeyEvent.VK_W) || input.getKey(KeyEvent.VK_UP)) {
			screen.yOffset--;
		}

		if (input.getKey(KeyEvent.VK_A) || input.getKey(KeyEvent.VK_LEFT)) {
			screen.xOffset--;
		}

		if (input.getKey(KeyEvent.VK_S) || input.getKey(KeyEvent.VK_DOWN)) {
			screen.yOffset++;
		}

		if (input.getKey(KeyEvent.VK_D) || input.getKey(KeyEvent.VK_RIGHT)) {
			screen.xOffset++;
		}
	}

	public void render() {
		BufferStrategy bufferStrategy = getBufferStrategy();

		if (bufferStrategy == null) {
			createBufferStrategy(1);

			return;
		}

		screen.render(pixels, WIDTH, 0);

		Graphics graphics = bufferStrategy.getDrawGraphics();

		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		graphics.dispose();

		bufferStrategy.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Game().start();
	}

}
