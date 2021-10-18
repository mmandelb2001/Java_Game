package game;

public class character {
	private int currentX;
	private int currentY;
	final int ID;
	public character(int x, int y, int iID){
		currentX = x;
		ID = iID;
		currentY = y;
	}
	public int getX(){return currentX;}
	public int getY(){return currentY;}
	public int getId(){return ID;}
	public void setX(int z){currentX = z;}
	public void setY(int z){currentY = z;}
}
