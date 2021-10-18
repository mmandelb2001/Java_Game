package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;

public class drawingComponent extends JComponent{
	private character[][] grid;
	private int max;
	private int size;
	private int gridPixleSize;
	player play;
	public boolean isPaused = false;
	public ArrayList<character> renderList;
	public Font f;
	int score;
	int health;
	public long currentFrames = 0;
	public long elapsedTime = 0;
	public int elapsedFrames = 0;
	public int fps = 0;
	public BufferedImage buffImg;
	static int timer;
	public boolean gameOver = false;
	public boolean mainMenu;
	public boolean gameRunning;
	public String direct = System.getProperty("user.dir");
	public File mainCharImg = new File(direct + "/src/images/mainch.png");
	public File starImg = new File(direct + "/src/images/star.png");
	public File enemyImg = new File(direct + "/src/images/enemych.png");

	public drawingComponent(player one, int maxt, int gridSize, int scored, int currentHealth, int time){
		System.out.println(direct);
		grid = new character[maxt][maxt];
		max = maxt;
		timer = time;
		health = currentHealth;
		size = maxt;
		elapsedFrames = 0;
		gridPixleSize = gridSize;
		gameRunning = false;
		play = one;
		mainMenu = true;
		gameOver = false;
		score = scored;
		buffImg = new BufferedImage(runner.size, runner.size, BufferedImage.TYPE_INT_ARGB);
		renderList = new ArrayList<character>();
		f = new Font("Veranda", Font.BOLD, (int)(gridPixleSize/24));
	}
	public void tick() {timer--;}
	public boolean getGameOver() {return gameOver;}
	public void setElapesFrames(int x) {elapsedFrames = x;}
	public void updateHealth(int x) {health = x;}
	public void startGame() {
		mainMenu = false;
		gameRunning = true;
	}
	public void add(character temp) {
		renderList.add(temp);
	}
	public void paintComponent(Graphics g){
		BufferedImageOp bio;
		Graphics2D g2 = (Graphics2D) g;
		int constant = gridPixleSize/max;
		try {
			BufferedImage in = ImageIO.read(starImg);
			BufferedImage in2 = ImageIO.read(mainCharImg);
			BufferedImage in3 = ImageIO.read(enemyImg);
;			for(int i = 0;i<renderList.size();i++) {
				if(renderList.get(i).getId() == 2) {
					g2.setColor(Color.blue);
					g2.drawImage(in2, renderList.get(i).getX()*constant, renderList.get(i).getY()*constant, null);
					
				}
				if(renderList.get(i).getId() == 4) {
					g2.setColor(Color.red);
					g2.drawImage(in3, renderList.get(i).getX()*constant, renderList.get(i).getY()*constant, null);
				}
				if(renderList.get(i).getId() == 3) {
					g2.setColor(Color.yellow);
					g2.fillRect(renderList.get(i).getX()*constant, renderList.get(i).getY()*constant, constant, constant);
				}
				if(renderList.get(i).getId() == 5) {
					g2.setColor(Color.green);
					g2.drawImage(in, renderList.get(i).getX()*constant, renderList.get(i).getY()*constant, null);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(isPaused == true) {g2.setFont(f);
			g2.setColor(Color.red);
			g2.drawString("Paused", (int) (gridPixleSize/2.5), (int) (gridPixleSize/2));
		}
		g2.setColor(Color.BLACK);
		g2.setFont(f);
		g2.drawString("Health: "+health, (int)(gridPixleSize*.08),(int)(gridPixleSize*.95));
		g2.drawString("Score: "+score, (int)(gridPixleSize*.75), (int)(gridPixleSize*.10));
		int elapsedSeconds = (int) (elapsedTime/1000);
		if(elapsedSeconds == 0) {elapsedSeconds = 1;}
		fps = (int) (elapsedFrames/elapsedSeconds);
		g2.drawString("Remaining Time: "+timer,(int) (gridPixleSize*.05), (int) (gridPixleSize*.05));
		if(health == 0) {gameOver = true;}
		if(timer == 0) {gameOver = true;}
		if(gameOver == true) {
			Font c = new Font("Veranda", Font.BOLD, (int)(gridPixleSize/10));
			g2.setFont(c);
			g2.drawString("GAME OVER", (int)(gridPixleSize*.20), (int)(gridPixleSize*.50));
		}
	}
	public void togglePause() {
		if(isPaused == false) {
			isPaused = true;
		}
		else {isPaused = false;}
	}
	public void setScore(int z) {score = z;}
}
