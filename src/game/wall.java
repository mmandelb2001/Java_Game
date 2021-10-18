package game;

public class wall extends character{
	public wall(int x, int y) {
		super(x, y, 3);
		// TODO Auto-generated constructor stub
	}
	public String toStrong() {
		return "X: "+this.getX()+" Y: "+this.getY();
	}
}
