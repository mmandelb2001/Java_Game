package game;

import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class player extends character{
	final int ID = 2;
	int dir = 0;
	private int currentX;
	private int currentY;
	int score = 0;
	int health;
	boolean hasMoved = false;
	boolean isInvincible = false;
	public player(int x, int y){
		super(x, y, 2);
		currentX = x;
		currentY = y;
		health = 3;
	}
	public void doDamage() {if(isInvincible == false) {health--;
		isInvincible = true;
		ScheduledExecutorService set =    Executors.newSingleThreadScheduledExecutor();
		set.schedule(new Runnable() {
			public void run() {
				isInvincible = false;
			}
		}, 2, TimeUnit.SECONDS);
	}
	}
	public boolean isInvincible() {return isInvincible;}
	public void setIsInvincible(boolean x) {isInvincible = x;}
	public int getHeatlh() {return health;}
	public boolean checkHasMoved() {return hasMoved;}
	public void setHasMoved() {hasMoved = true;}
	public void setDir(int x) {dir = x;}
	public int getDir() {return dir;}
	public void addOne() {score++;}
	public int getScore() {return score;}
}
