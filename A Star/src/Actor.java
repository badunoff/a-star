import java.awt.Point;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Actor {
	Point cur_loc;
	Point start;
	Point goal; 
	Field field;
	
	int totalExpanded = 0;
	int counter = 0;
	
	MapCell[][] known_map;
	Plan plan;
	MapCell above, below, left, right;
	
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
			known_map[above.getLocation().x][above.getLocation().y].setOpen(field.check_loc(above.getLocation())); 
		}
		
		if(below != null){ //check down
			known_map[below.getLocation().x][below.getLocation().y].setOpen(field.check_loc(below.getLocation()));
		}
		
		if(left != null){ //check left
			known_map[left.getLocation().x][left.getLocation().y].setOpen(field.check_loc(left.getLocation()));
		}
		
		if(right != null){ //check right
			known_map[right.getLocation().x][right.getLocation().y].setOpen(field.check_loc(right.getLocation()));
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
			  above = known_map[cur_loc.x][cur_loc.y + 1]; 
		else  above = null;
		
		if( cur_loc.x >= 0 
		 && cur_loc.x < known_map.length
		 && cur_loc.y - 1 >= 0
		 && cur_loc.y - 1 < known_map[0].length){ //check down
			below = known_map[cur_loc.x][cur_loc.y - 1]; 
		}
		else{
			below = null;
		}
		
		if( cur_loc.x - 1 >= 0
		 && cur_loc.x - 1 < known_map.length 
		 && cur_loc.y >= 0
		 && cur_loc.y < known_map[0].length){ //check left
			left = known_map[cur_loc.x - 1][cur_loc.y]; 
		}
		else{
			left = null;
		}
		
		if( cur_loc.x + 1 >= 0
		 && cur_loc.x + 1 < known_map.length
		 && cur_loc.y >= 0
		 && cur_loc.y < known_map[0].length){ //check right
			right =  known_map[cur_loc.x + 1][cur_loc.y];
		}
		else{
			right = null;
		}
	}
	
	public boolean checkPlan(){
		Point nextStep = plan.peekNextStep();
		
		if((nextStep.equals(above) && !above.isOpen())
		 ||(nextStep.equals(below) && !below.isOpen())
		 ||(nextStep.equals(left) && !left.isOpen())
		 ||(nextStep.equals(right) && !right.isOpen())){
			return false;
		}
		else{
			return true;
		}
	}
	
	
	/**
	    * @return true if valid move
	*/
	public boolean moveUp(){
		if(above != null && known_map[above.getLocation().x][above.getLocation().y].isOpen()){ //check up
			cur_loc.setLocation(above.getLocation().x, above.getLocation().y);
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
		if(below != null && known_map[below.getLocation().x][below.getLocation().y].isOpen()){ //check down
			cur_loc.setLocation(below.getLocation().x, below.getLocation().y);
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
		if(right != null && known_map[right.getLocation().x][right.getLocation().y].isOpen()){ //check down
			cur_loc.setLocation(right.getLocation().x, right.getLocation().y);
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
		if(left != null && known_map[left.getLocation().x][left.getLocation().y].isOpen()){ //check down
			cur_loc.setLocation(left.getLocation().x, left.getLocation().y);
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
		if( (above != null && pt.equals(above.getLocation()) && above.isOpen())
		 || (below != null && pt.equals(below.getLocation()) && below.isOpen())
		 || (left != null && pt.equals(left.getLocation()) && left.isOpen())
		 || (right != null && pt.equals(right.getLocation()) && right.isOpen())){
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
	 * @throws Exception 
	*/
	public StepResult step(Method method){
		if(counter==0)
		{
			counter++;
			switch(method){
				case Forward:
					plan = PathComputer.ComputePathForward(known_map, cur_loc, goal, counter);
					break;
				case Backward:
					plan = PathComputer.ComputePathBackwards(known_map, cur_loc, goal, counter);
					break;
				case Adaptive:
					plan = PathComputer.ComputePathAdaptive(known_map, cur_loc, goal, counter);
					break;
				default:
					break;
			}
			
			if(plan == null){
				return StepResult.NoPath;
			}
			totalExpanded += plan.getExp();
			//System.out.println(plan);
		}
		
		if(!goTo(plan.remove()))
		{
			counter++;
			
			switch(method){
				case Forward:
					plan = PathComputer.ComputePathForward(known_map, cur_loc, goal, counter);
					break;
				case Backward:
					plan = PathComputer.ComputePathBackwards(known_map, cur_loc, goal, counter);
					break;
				case Adaptive:
					plan = PathComputer.ComputePathAdaptive(known_map, cur_loc, goal, counter);
					break;
				default:
					break;
			}
			
			if(plan == null){
				return StepResult.NoPath;
			}
			
			totalExpanded += plan.getExp();
			/*System.out.println(plan);
			System.out.println("New Path:");
			System.out.println(this);
			promptEnterKey();*/
			goTo(plan.remove());
		}
		
		
		if(cur_loc.equals(goal)){
			return StepResult.Success;
		}
		else{
			return StepResult.Step;
		}
	}
	
	/**
	    * Tries to moves the Actor in a random direction
	    * @return true if goal is reached
	*/
	public StepResult drunkStep(){
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
			return StepResult.Success;
		}
		else{
			return StepResult.Step;
		}
	}
	
	/**
	    * Moves the Actor in a random valid direction
	    * @return true if goal is reached
	*/
	public StepResult dumbStep(){
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
			return StepResult.Success;
		}
		else{
			return StepResult.Step;
		}
	}
	
	
	
	public String toString(){
		return arrayToString(known_map, cur_loc, start, goal, plan);
	}
	
	public static String arrayToString(MapCell[][] arr, Point obj, Point start, Point finish, Plan plan){
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
				Point newPoint = new Point(x, y);
				
				if(newPoint.equals(obj)){
					retval += " X .";
				}
				else if(newPoint.equals(start)){
					retval += "> <.";
				}
				else if(newPoint.equals(finish)){
					retval += "< >.";
				}
				else if(plan != null && plan.contains(newPoint) && !arr[x][y].isOpen()){
					retval += "!!!!";
				}
				else if(plan != null && plan.contains(newPoint)){
					retval += " x .";
				}
				else {
					retval += arr[x][y].toString();
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
	
	public void printExp()
	{
		System.out.println("Total expanded nodes: " + totalExpanded + "\n");
	}
	
	public static void promptEnterKey(){
		   System.out.println("Press \"ENTER\" to continue...");
		   Scanner scanner = new Scanner(System.in);
		   scanner.nextLine();
		}
}