package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class runner extends JPanel{
	final static long startTime = System.currentTimeMillis();
	static int counter = 0;
	static MyKeyListener list;
	static player play;
	static grid gameGrid;
	public static int size = 600;
	static int gridSize = 10;
	static enemy[] enemies;
	static boolean isRunning = true;
	static boolean isPaused = false;
	static drawingComponent DC;
	static boolean removePause = false;
	static JFrame frame;
	static boolean firstTime;
	static boolean moveEnemies = false;
	static boolean mainMenu = false;
	static boolean isRunningFirstTime = true;
	public runner(){
		MyKeyListener listener = new MyKeyListener(this);
		counter = 0;
		player one = new player(1, 1);
		enemy[] y = new enemy[3];
		y[0] = new enemy(8, 2);
		y[1] = new enemy(6, 7);
		y[2] = new enemy(2, 8);
		enemies = new enemy[y.length];
		enemies = y;
		grid Grid = new grid(gridSize, gridSize, size, one, enemies);
		play = one;
		gameGrid = Grid;
		list = listener;
		firstTime = true;
		this.addKeyListener(listener);
		mainMenu = true;
		frame = new JFrame("game");
		setFocusable(true);
	}
	public static void main(String[] args){	
	runner runn = new runner();
	int frameSized = (int) (size*1.1);
	frame.setSize(frameSized, frameSized);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	frame.add(runn);
	frame.setBackground(Color.gray);
	frame.addKeyListener(list);
	mainMenu();
	}
	public static void toggleMoveEnemies() {
		if(moveEnemies == true) {moveEnemies = false;}
		if(moveEnemies == false) {moveEnemies = true;}
	}
	public static void togglePause() {
		if(isPaused == false) {isPaused = true;}
		else {isPaused = false;}
	}
	public static void mainMenu() {
		frame.add(gameGrid.update());
		frame.toFront();
		runGame(0);


	}
	public player getPlayer() {return play;}
	public static void doNothing() {return;}
	public void paintComponent(Graphics g){}
	public static void toggleRemovePause() {if(removePause == true) {removePause = false;}
	else {removePause = true;}
	}
	public static void runGame(int difficulty) {
		gameGrid.setDifficulty(difficulty);
		while(isRunning) {
			frame.requestFocus();
			DC = gameGrid.update();
			if(DC.getGameOver() == true) {
				isRunning = false;
			}
			if(firstTime == true) {
				Runnable runner = new Runnable() {
					public void run() {
						for(int i = 0;i<enemies.length;i++) {
							boolean moved = false;
							while(moved == false) {
								int r = (int) (Math.random()*4);
								if(isPaused == true) {
									doNothing();
								}
								if(r == 0 && isPaused == false) {
									if(gameGrid.moveUp(enemies[i], enemies[i].getX(), enemies[i].getY()) == 0) {
										//gameGrid.moveUp(enemies[i], enemies[i].getX(), enemies[i].getY());
										moved = true;
									}
								}
								else if(r == 1 && isPaused == false) {
									if(gameGrid.moveDown(enemies[i], enemies[i].getX(), enemies[i].getY()) == 0) {
										//gameGrid.moveDown(enemies[i], enemies[i].getX(), enemies[i].getY());
										moved = true;
									}
								}
								else if(r == 2 && isPaused == false) {
									if(gameGrid.moveLeft(enemies[i], enemies[i].getX(), enemies[i].getY()) == 0) {
										//gameGrid.moveLeft(enemies[i], enemies[i].getX(), enemies[i].getY());
										moved = true;
									}
								}
								else if(r == 3 && isPaused == false) {
									if(gameGrid.moveRight(enemies[i], enemies[i].getX(), enemies[i].getY()) == 0) {
										//gameGrid.moveRight(enemies[i], enemies[i].getX(), enemies[i].getY());
										moved = true;
									}
								}
								
							}
						}
					}
				};
				 ScheduledExecutorService sec =    Executors.newScheduledThreadPool(5);
				 sec.scheduleAtFixedRate(new Runnable() {
					 public void run() {
						 if(isPaused == false) {
						 DC.tick();}
					 }
				 }, 1, 1, TimeUnit.SECONDS);
				 sec.scheduleAtFixedRate(runner , 2, 2, TimeUnit.SECONDS);
				 firstTime = false;
			}
			if(isPaused == false) {
			if(list.getDir() == 1) {
			gameGrid.moveUp(play, play.getX(), play.getY());
			list.setDir(0);
			}
			else if(list.getDir() == 2) {
			gameGrid.moveDown(play, play.getX(), play.getY());
			list.setDir(0);
			}
			else if(list.getDir() == 3) {
			gameGrid.moveLeft(play, play.getX(), play.getY());
			list.setDir(0);
			}
			else if(list.getDir() == 4) {
			gameGrid.moveRight(play, play.getX(), play.getY());
			list.setDir(0);
			}
			}
			JButton resume = new JButton("resume");
			resume.setBounds((int)(size*.38), (int)(size*.54), (int)(size*.15), (int)(size*.05));
			if(removePause == true) {
				frame.remove(resume);
				frame.getContentPane().remove(resume);
				frame.getContentPane().validate();
				frame.repaint();
				frame.revalidate();
				removePause = false;
				System.out.println(removePause);
			}
			if(list.getDir() == 5) {
				togglePause();
				DC.togglePause();
				list.setDir(0);
			}
			DC = gameGrid.update();
			frame.add(DC);
			frame.repaint();
			}
	}
	
}

class MyKeyListener implements KeyListener {
	player play;
	runner runn;
	public int dir;
	public MyKeyListener(runner x){
		runn = x;
		dir = 0;
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W){
			//System.out.println("W was pressed");
			dir = 1;
		}
		if(key == KeyEvent.VK_ESCAPE) {
			dir = 5;
		}
		if(key == KeyEvent.VK_A) {
			//System.out.println("A was pressed");
			dir = 3;
		}
		if(key == KeyEvent.VK_S) {
			//System.out.println("S was pressed");
			dir = 2;
		}
		if(key == KeyEvent.VK_D) {
			//System.out.println("D was pressed");
			dir = 4;
		}
	}
public void keyReleased(KeyEvent e) {
		//not going to be used
	}
public void keyTyped(KeyEvent e) {
		//not going to be used
	}
	public int getDir() {return dir;}
	public void setDir(int x) {dir = x;}
}
