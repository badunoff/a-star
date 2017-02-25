import java.awt.Point;
import java.util.Scanner;

public class Driver {
	public static final int DIMX = 101;
	public static final int DIMY = 101;
	public static final int PERCENT_OPEN = 70;
	public static final int NUM_MAPS = 150;
	
	public static void main(String[] args){ 
		
		generateMaps(NUM_MAPS);
		
		Point start = new Point();
		Point finish = new Point();
		
		int forwardSteps = 0;
		int forwardNodes = 0;

		int backwardSteps = 0;
		int backwardNodes = 0;
		
		int adaptiveSteps = 0;
		int adaptiveNodes = 0;
		
		for(int j = NUM_MAPS; j > 0; j--){
			System.out.println("Map " + j);
			Field field = ReadAndWriteBoard.read("Map" + j, start, finish);
			
			Point temp = new Point();
			
			temp = runMap(field, start, finish, Method.Forward);
			forwardSteps += temp.x;
			forwardNodes += temp.y;
			
			temp = runMap(field, start, finish, Method.Backward);
			backwardSteps += temp.x;
			backwardNodes += temp.y;
			
			temp = runMap(field, start, finish, Method.Adaptive);
			adaptiveSteps += temp.x;
			adaptiveNodes += temp.y;
		}
		
		
		System.out.println("Forward Step Average: " + (forwardSteps/NUM_MAPS));
		System.out.println("Forward Expansions Average: " + (forwardNodes/NUM_MAPS));
		

		System.out.println("Backward Step Average: " + (backwardSteps/NUM_MAPS));
		System.out.println("Backward Expansions Average: " + (backwardNodes/NUM_MAPS));

		System.out.println("Adaptive Step Average: " + (adaptiveSteps/NUM_MAPS));
		System.out.println("Adaptive Expansions Average: " + (adaptiveNodes/NUM_MAPS));
		
		
		/*
		Point start = new Point();
		Point finish = new Point();
		
		Field field = ReadAndWriteBoard.read("Map2", start, finish);
		
		System.out.println(start);
		System.out.println(finish);
		System.out.println(Field.arrayToString(field.field, start, finish));
		
		
		Actor actor1 = new Actor(field, DIMX, DIMY, start, finish);
		Actor actor2 = new Actor(field, DIMX, DIMY, start, finish);
		Actor actor3 = new Actor(field, DIMX, DIMY, start, finish);
		
		//System.out.println(actor);
		
		promptEnterKey();
		
		for(int i = 1; i < 10000; i++ ){
			System.out.println(i);
			StepResult step1 = StepResult.Step;
			StepResult step2 = StepResult.Step;
			StepResult step3 = StepResult.Step;
			
			try {
				if(step1 == StepResult.Step){
					step1 = actor1.step(Method.Forward);
					System.out.println("FORWARD: ");
					System.out.println(actor1);
				}
				
				if(step2 == StepResult.Step){
					step2 = actor2.step(Method.Backward);
					System.out.println("BACKWARD: ");
					System.out.println(actor2);
				}
				
				if(step3 == StepResult.Step){
					step3 = actor3.step(Method.Adaptive);
					System.out.println("ADAPTIVE: ");
					System.out.println(actor3);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			promptEnterKey();
		}*/
		
	}
	
	public static Point runMap(Field field, Point start, Point finish, Method method){
		Actor actor = new Actor(field, DIMX, DIMY, start, finish);
		String type = "";
		
		switch(method){
			case Forward:
				type = "Forward";
				break;
			case Backward:
				type = "Backward";
				break;
			case Adaptive:
				type = "Adaptive";
				break;
			default:
				break;
		}
		
		boolean br = false; 
		int i = 0;
		while(!br){
			StepResult step;
			try {
				step = actor.step(method);
				i++;
				
				switch(step){
					case Success:
						System.out.println("\t" + type + " Actor");
						System.out.println("\t\tSuccess!");
						System.out.println("\t\tSteps taken: " + i);
						System.out.print("\t\t");
						actor.printExp();
						br = true;
						break;
					case NoPath:
						System.out.println("\t" + type + " Actor");
						System.out.println("\t\tNo Path Found");
						System.out.println("\t\tSteps taken: " + i);
						System.out.print("\t\t");
						actor.printExp();
						br = true;
						break;
					default:
						break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new Point(i, actor.getExpanded());
	}
	
	
	
	public static void promptEnterKey(){
		   System.out.println("Press \"ENTER\" to continue...");
		   Scanner scanner = new Scanner(System.in);
		   scanner.nextLine();
		}
	
	public static void generateMaps(int numMaps){
		for(int i = numMaps; i > 0; i--){
			generateMap("Map" + i, DIMX, DIMY, PERCENT_OPEN);
		}
	}
	
	public  static void generateMap(String name, int dimX, int dimY, int percentOpen){
		Field field = new Field(dimX, dimY);
		field.random_percent_plain_field(percentOpen);
		
		Point start = field.randomValidLocation();
		Point finish = field.randomValidLocation(start);
		
		ReadAndWriteBoard.write(field.getFieldArrayCopy(), start, finish, name);
	}
}