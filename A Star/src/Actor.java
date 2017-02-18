import java.awt.Point;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;


public class Actor {
	Point cur_loc;
	Point start;
	Point goal;
	Field field;
	
	boolean[][] known_map;
	Queue<Point> queue;
	Point above, below, left, right;
	
	public Actor(Field field, int mapx, int mapy, Point start, Point finish){
		this.start = (Point) start.clone();
		this.goal = (Point) finish.clone();
		this.cur_loc = (Point) start.clone();
		
		this.field = field;
		
		this.known_map = new boolean[mapx][mapy];
		for (boolean[] row: known_map)
			Arrays.fill(row, true);
		this.queue = null;
		
		setNeighbors();
		checkNeighbors();
	}
	
	private void checkNeighbors(){
		if(above != null){ //check up
			known_map[above.x][above.y] = field.check_loc(above); 
		}
		
		if(below != null){ //check down
			known_map[below.x][below.y] = field.check_loc(below);
		}
		
		if(left != null){ //check left
			known_map[left.x][left.y] = field.check_loc(left);
		}
		
		if(right != null){ //check right
			known_map[right.x][right.y] = field.check_loc(right);
		}
	}
	
	private void setNeighbors(){
		if(cur_loc.x >= 0 && cur_loc.x < known_map.length && cur_loc.y + 1 >= 0 && cur_loc.y + 1 < known_map[0].length){ //check up
			above = new Point(cur_loc.x, cur_loc.y + 1); 
		}
		else{
			above = null;
		}
		
		if(cur_loc.x >= 0 && cur_loc.x < known_map.length && cur_loc.y - 1 >= 0 && cur_loc.y - 1 < known_map[0].length){ //check down
			below = new Point(cur_loc.x, cur_loc.y - 1); 
		}
		else{
			below = null;
		}
		
		if(cur_loc.x - 1 >= 0 && cur_loc.x - 1 < known_map.length && cur_loc.y >= 0 && cur_loc.y < known_map[0].length){ //check left
			left = new Point(cur_loc.x - 1, cur_loc.y); 
		}
		else{
			left = null;
		}
		
		if(cur_loc.x + 1 >= 0 && cur_loc.x + 1 < known_map.length && cur_loc.y >= 0 && cur_loc.y < known_map[0].length){ //check right
			right =  new Point(cur_loc.x + 1, cur_loc.y);
		}
		else{
			right = null;
		}
	}
	
	public boolean moveUp(){
		if(above != null && known_map[above.x][above.y]){ //check up
			cur_loc.setLocation(above.x, above.y);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean moveDown(){
		if(below != null && known_map[below.x][below.y]){ //check down
			cur_loc.setLocation(below.x, below.y);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean moveRight(){
		if(right != null && known_map[right.x][right.y]){ //check down
			cur_loc.setLocation(right.x, right.y);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean moveLeft(){
		if(left != null && known_map[left.x][left.y]){ //check down
			cur_loc.setLocation(left.x, left.y);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean goTo(Point pt){
		if(pt.equals(above) || pt.equals(above) || pt.equals(left) || pt.equals(right)){
			cur_loc.setLocation(pt);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean step(){
		goTo(queue.remove());
		if(cur_loc.equals(goal)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean drunkStep(){
		Random random = new Random();
		
		switch(random.nextInt(4)){
			case 0:
				System.out.println("UP");
				moveUp();
				break;
			case 1:
				System.out.println("DOWN");
				moveDown();
				break;
			case 2:
				System.out.println("LEFT");
				moveLeft();
				break;
			case 3:
				System.out.println("RIGHT");
				moveRight();
				break;
			default:
				break;
		}
		
		if(cur_loc.equals(goal)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean dumbStep(){
		Random random = new Random();
		Boolean validStep = false;
		
		while(!validStep){
			switch(random.nextInt(4)){
				case 0:
					if(moveUp()){
						System.out.println("UP");
						validStep = true;
					}
					break;
				case 1:
					if(moveDown()){
						System.out.println("DOWN");
						validStep = true;
					}
					break;
				case 2:
					if(moveLeft()){
						System.out.println("LEFT");
						validStep = true;
					}
					break;
				case 3:
					if(moveRight()){
						System.out.println("RIGHT");
						validStep = true;
					}
					break;
				default:
					break;
			}
		}
		
		if(cur_loc.equals(goal)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	public String toString(){
		return Field.arrayToString(known_map, cur_loc, start, goal);
	}
}
