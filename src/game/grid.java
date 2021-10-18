package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class grid extends JPanel {
	static character[][] grid;
	static int itemSize;
	static int gridSized;
	static int gridPixleSize;
	static int counter;
	public boolean justDidDamage = false;
	player one;
	food foodie;
	static enemy[] enemies;
	static drawingComponent DC;
	public grid(int size, int itemSizes, int gridSize, player play, enemy[] x){
		grid = new character[size][size];
		itemSize = itemSizes;
		gridPixleSize = gridSize;
		gridSized = size;
		fill();
		one = play;
		add(one, one.getX(), one.getY());
		foodie = new food(8, 8);
		counter = 0;
		enemies = new enemy[x.length];
		enemies = x;
		DC = new drawingComponent(one, itemSize, gridPixleSize, one.getScore(), one.getHeatlh(), 30);
		DC.add(one);
		DC.add(foodie);
	}
	public void setDifficulty(int x) {
		createMap(x, enemies);
	}
	public static void createMap(int difficulty, enemy[] x) {
		if(difficulty == 0) {
		wall[] first = new wall[26];
		first[0] = new wall(0, 7);
		first[1] = new wall(0, 8);
		first[2] = new wall(0, 9);
		first[3] = new wall(0, 6);
		first[4] = new wall(1, 9);
		first[5] = new wall(2, 9);
		first[6] = new wall(6, 9);
		first[7] = new wall(7, 9);
		first[8] = new wall(8, 9);
		first[9] = new wall(9, 9);
		first[10] = new wall(6, 8);
		first[11] = new wall(9, 8);
		first[12] = new wall(9, 7);
		first[13] = new wall(9, 6);
		first[14] = new wall(9, 5);
		first[15] = new wall(4, 6);
		first[16] = new wall(4, 5);
		first[17] = new wall(4, 4);
		first[18] = new wall(4, 3);
		first[19] = new wall(5, 3);
		first[20] = new wall(6, 3);
		first[21] = new wall(7, 3);
		first[22] = new wall(8, 3);
		first[23] = new wall(0, 0);
		first[24] = new wall(0, 1);
		first[25] = new wall(1, 0);
		for(int i = 0;i<x.length;i++) {
			DC.add(x[i]);
		}
		loadMap(first);
		}
	}


	public static void loadMap( wall[] that) {
		wall[] current = new wall[23];
		current = that;
		for(int i = 0;i<current.length;i++) {
			wall temp = current[i];
			add(current[i], current[i].getX(), current[i].getY());
			DC.add(current[i]);
		}
	}
	public static void fill(){
		for(int i = 0;i<grid.length;i++){
			for(int q =0;q<grid[i].length;q++){
				grid[i][q] = new emptySpace(i, q);
			}
		}
	}
	public static void giveRandomPosition(character that) {
		int x;
		int y;
		x = (int) (Math.random()*itemSize);
		y = (int) (Math.random()*itemSize);
		boolean foundGoodLoc = false;
		while(foundGoodLoc == false) {
			if(isValid(x, y) == true && isOccupied(x, y) == false) {
				foundGoodLoc = true;
			}else {
				x = (int) (Math.random()*itemSize);
				y = (int) (Math.random()*itemSize);
			}
		} 
		that.setX(x);
		that.setY(y);
	}
	public void startGame() {
		DC.startGame();
	}
	public drawingComponent update(){
		DC.setScore(one.getScore());
		if(sameSpot(one, foodie) == true) {
			one.addOne();
			giveRandomPosition(foodie);
		}
		for(int i = 0;i<enemies.length;i++) {
			if(sameSpot(one, enemies[i]) == true) {
				one.doDamage();
				justDidDamage = true;
			}
		}
		DC.updateHealth(one.getHeatlh());
		return DC;
	}
	public static boolean isValid(int x, int y){
		if(x<gridSized && y<gridSized && x >= 0 && y >= 0){
			return true;
		}return false;
	}
	public static boolean isOccupied(int x, int y){
		if(grid[x][y].getId() == 0){return false;}
		if(grid[x][y].getId() == 4) {return false;}
		if(grid[x][y].getId() == 3) {return true;}
		return true;
	}
	public static void left(character self, int x, int y) {
		if(isValid(x-1, y) == true && isOccupied(x-1, y) == false){
		grid[x][y] = new emptySpace(x, y);
		grid[x-1][y] = self;
		self.setX(x-1);}
	}
	public static void right(character self, int x, int y) {
		if(isValid(x+1, y) == true && isOccupied(x+1, y) == false){
		grid[x][y] = new emptySpace(x, y);
		grid[x+1][y] = self;
		self.setX(x+1);}
	}
	public static void up(character self, int x, int y) {
		if(isValid(x, y-1) == true && isOccupied(x, y-1) == false){
		grid[x][y] = new emptySpace(x, y);
		grid[x][y-1] = self;
		self.setY(y-1);}
	}
	public static void down(character self, int x, int y) {
		if(isValid(x, y+1) == true && isOccupied(x, y+1) == false){
		grid[x][y] = new emptySpace(x, y);
		grid[x][y+1] = self;
		self.setY(y+1);}
	}
	public int moveLeft(character self, int x, int y){
		if(isValid(x-1, y) == true && isOccupied(x-1, y) == false){
			grid[x][y] = new emptySpace(x, y);
			grid[x-1][y] = self;
			self.setX(x-1);
			return 0;}
		return -1;
	}
	public int moveRight(character self, int x, int y){
		if(isValid(x+1, y) == true && isOccupied(x+1, y) == false){
			grid[x][y] = new emptySpace(x, y);
			grid[x+1][y] = self;
			self.setX(x+1);
			return 0;}
		return -1;
		}

	public static void add(character self, int x, int y){
		if(isValid(x, y) == true){
			grid[x][y] = self;}
	}
	public boolean sameSpot(character first, character second) {
		if(first.getX() == second.getX() && first.getY() == second.getY()){
			return true;
		}
		return false;
	}
	public static void remove(int x, int y) {grid[x][y] = new emptySpace(x, y);}
	
	public int moveUp(character self, int x, int y){
		if(isValid(x, y-1) == true && isOccupied(x, y-1) == false){
			grid[x][y] = new emptySpace(x, y);
			grid[x][y-1] = self;
			self.setY(y-1);
			return 0;
		}
		return -1;
	}
	public int moveDown(character self, int x, int y){
		if(isValid(x, y+1) == true && isOccupied(x, y+1) == false){
			grid[x][y] = new emptySpace(x, y);
			grid[x][y+1] = self;
			self.setY(y+1);
			return 0;}
		return -1;
		}
	}

