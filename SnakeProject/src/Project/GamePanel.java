package Project;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	//Initializing dimentions
	static final int SCREEN_WIDTH = 500;
	static final int SCREEN_HEIGHT = 500;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 100;
	
	//Cordinates of Movement
	final int x[] = new int[GAME_UNITS]; 
	final int y[] = new int[GAME_UNITS];

	int bodyParts =6;
	int dotEaten = 0;
	int dotX;
	int dotY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		//Setting Game Panel
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	
	}
	
	//Starting game
	public void startGame() {
		newDot();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void changeSnakeColor(Graphics g) {
		g.setColor(Color.red);
	}
	
	public void draw(Graphics g) {
		
		//Creating a Grid
		for (int i = 0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		}
		
		if(running) {
			g.setColor(Color.red);
			g.fillOval(dotX, dotY,UNIT_SIZE,UNIT_SIZE);
		
			//Create BodyParts
			for(int i = 0; i<bodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//Setting Score 
			g.setColor(Color.yellow);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			FontMetrics m = getFontMetrics(g.getFont());
			g.drawString("Score: "+dotEaten, (SCREEN_WIDTH - m.stringWidth("Score: "+dotEaten))/2, g.getFont().getSize());
		} else {
			
			gameOver(g);
		}
		
	}
	
	//Creating new dot
	public void newDot() {
		dotX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		dotY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void move() {
		//Snake Movement
		for(int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}

		//Movement of snake when direction changed
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}

	//Checking if the dot is eaten
	public void checkDot() {
		if((x[0] == dotX) && (y[0]== dotY)) {
			bodyParts++;
			dotEaten++;
			newDot();
		}
	}
	public void checkCollision() {
		//Check if head bites the body
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && y[0] == y[i]) {
				running = false;
			}
		}
		
		//Check if head touchs the border
		if(x[0] < 0) {
			running = false;
			
		}
		if(x[0] > SCREEN_WIDTH - UNIT_SIZE) {
			running = false;
		}
		if(y[0] < 0) {
			running = false;
		}
		if(y[0] > SCREEN_HEIGHT - UNIT_SIZE) {
			running = false;
		}
		if (!running) {
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g) {
		
		//Displaying Final Score
		g.setColor(Color.yellow);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		FontMetrics m1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+dotEaten, (SCREEN_WIDTH - m1.stringWidth("Score: "+dotEaten))/2, g.getFont().getSize());
		
		g.setColor(Color.red);
		g.setFont(new Font("Helvetica", Font.BOLD, 60));
		FontMetrics m = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - m.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(running) {
			move();
			checkDot();
			checkCollision();
		}
		repaint();
		
	}

	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent key) {
			
			//Key Eveent Handling 
			switch(key.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if(direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L') {
						direction = 'R';
					}
					break;
				
				case KeyEvent.VK_UP:
					if(direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if(direction != 'U') {
						direction = 'D';
					}
					break;
			}
		
	}

}}
