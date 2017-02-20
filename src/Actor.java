import java.awt.Point;
import java.util.Arrays;
import java.util.Random;


public class Actor {
	Point cur_loc;
	Point start;
	Point goal; 
	Field field;
	
	int counter = 0;
	
	MapCell[][] known_map;
	Plan plan;
	Point above, below, left, right;
	
	public Actor(Field field, int mapx, int mapy, Point start, Point finish){
		this.start = (Point) start.clone();
		this.goal = (Point) finish.clone();
		this.cur_loc = (Point) start.clone();
		
		this.field = field;
		
		this.known_map = new MapCell[mapx][mapy];
		for (int x = 0; x < mapx; x++)
			for (int y = 0; y < mapy; y++)
				 this.known_map[x][y] = new MapCell(new Point(x,y));
		this.plan = null;
		
		setNeighbors();
		checkNeighbors();
	}
	
	/**
	    * sets neighbors' open values.
	*/
	private void checkNeighbors(){
		if(above != null){ //check up
			known_map[above.x][above.y].setOpen(field.check_loc(above)); 
		}
		
		if(below != null){ //check down
			known_map[below.x][below.y].setOpen(field.check_loc(below));
		}
		
		if(left != null){ //check left
			known_map[left.x][left.y].setOpen(field.check_loc(left));
		}
		
		if(right != null){ //check right
			known_map[right.x][right.y].setOpen(field.check_loc(right));
		}
	}
	
	/**
	    * sets coordinates of neighbors and null if they are out of bounds.
	*/
	private void setNeighbors()
	{
		if( cur_loc.x >= 0
		 && cur_loc.x < known_map.length
		 && cur_loc.y + 1 >= 0
		 && cur_loc.y + 1 < known_map[0].length) //check up
			  above = new Point(cur_loc.x, cur_loc.y + 1); 
		else  above = null;
		
		if( cur_loc.x >= 0 
		 && cur_loc.x < known_map.length
		 && cur_loc.y - 1 >= 0
		 && cur_loc.y - 1 < known_map[0].length){ //check down
			below = new Point(cur_loc.x, cur_loc.y - 1); 
		}
		else{
			below = null;
		}
		
		if( cur_loc.x - 1 >= 0
		 && cur_loc.x - 1 < known_map.length 
		 && cur_loc.y >= 0
		 && cur_loc.y < known_map[0].length){ //check left
			left = new Point(cur_loc.x - 1, cur_loc.y); 
		}
		else{
			left = null;
		}
		
		if( cur_loc.x + 1 >= 0
		 && cur_loc.x + 1 < known_map.length
		 && cur_loc.y >= 0
		 && cur_loc.y < known_map[0].length){ //check right
			right =  new Point(cur_loc.x + 1, cur_loc.y);
		}
		else{
			right = null;
		}
	}
	
	/**
	    * @return true if valid move
	*/
	public boolean moveUp(){
		if(above != null && known_map[above.x][above.y].isOpen()){ //check up
			cur_loc.setLocation(above.x, above.y);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	    * @return true if valid move
	*/
	public boolean moveDown(){
		if(below != null && known_map[below.x][below.y].isOpen()){ //check down
			cur_loc.setLocation(below.x, below.y);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	    * @return true if valid move
	*/
	public boolean moveRight(){
		if(right != null && known_map[right.x][right.y].isOpen()){ //check down
			cur_loc.setLocation(right.x, right.y);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	    * @return true if valid move
	*/
	public boolean moveLeft(){
		if(left != null && known_map[left.x][left.y].isOpen()){ //check down
			cur_loc.setLocation(left.x, left.y);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	    * Move actor to a point in one step, if possible
	    * @param pt The potential new location
	    * @return true if valid movement and false if otherwise
	*/
	public boolean goTo(Point pt){
		if(pt.equals(above) || pt.equals(below) || pt.equals(left) || pt.equals(right)){
			cur_loc.setLocation(pt);
			setNeighbors();
			checkNeighbors();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	    * Moves the Actor 1 step along the path in queue
	    * @return true if goal is reached
	*/
	public boolean step(){
		if(counter==0)
		{
			counter++;
			plan = PathComputer.ComputePathForward(known_map, start, goal, counter);
		}
		if(!goTo(plan.remove()))
		{
			counter++;
			plan = PathComputer.ComputePathForward(known_map, start, goal, counter);
			goTo(plan.remove());
		}
		if(cur_loc.equals(goal)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	    * Tries to moves the Actor in a random direction
	    * @return true if goal is reached
	*/
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
	
	/**
	    * Moves the Actor in a random valid direction
	    * @return true if goal is reached
	*/
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
		return arrayToString(known_map, cur_loc, start, goal);
	}
	
	public static String arrayToString(MapCell[][] arr, Point obj, Point start, Point finish){
		String retval;

		//Top
		retval = "+";
		for(int i = 0; i < arr.length; i++){
			retval += "----";
		}
		retval += "+Y\n";
		
		for(int y = arr[0].length - 1; y >= 0; y--){
			retval += "|";
			for(int x = 0; x < arr.length; x++){
				if(x == obj.x && y == obj.y){
					retval += " X .";
				}
				else if(x == start.x && y == start.y){
					retval += "> <.";
				}
				else if(x == finish.x && y == finish.y){
					retval += "< >.";
				}
				else if(arr[x][y].isOpen()){
					retval += "   .";
				}
				else{
					retval += "||||";
				}
			}
			retval += "|" + y + "\n";
		}
		
		retval += "+";
		for(int i = 0; i < arr.length; i++){
			retval += "----";
		}
		retval += "+\n";
		
		retval += " ";
		for(int i = 0; i < arr.length; i++){
			char[] chars = new char[4 - String.valueOf(i).length()];
			Arrays.fill(chars, ' ');
			
			retval += i + new String(chars);
		}
		retval += "X\n";
		
		return retval;
	}
}
