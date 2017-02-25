import java.awt.Point;
import java.util.Scanner;

public class Driver {
	public static final int DIMX = 20;
	public static final int DIMY = 20;
	public static final int PERCENT_OPEN = 70;
	
	public static void main(String[] args){ 
		
		generateMaps(50);
		
		Point start = new Point();
		Point finish = new Point();
		
		for(int j = 50; j > 0; j--){
			System.out.println("Map " + j);
			Field field = ReadAndWriteBoard.read("Map" + j, start, finish);
			
			runMap(field, start, finish, Method.Forward);
			runMap(field, start, finish, Method.Backward);
			runMap(field, start, finish, Method.Adaptive);
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		
		
		
		
		
		Field field = new Field(DIMX, DIMY);
		field.random_percent_plain_field(70);
		
		Point start = field.randomValidLocation();
		Point finish = field.randomValidLocation(start);
		
		System.out.println(start);
		System.out.println(finish);
		System.out.println(Field.arrayToString(field.field, start, finish));
		
		
		Actor actor = new Actor(field, DIMX, DIMY, start, finish);
		
		System.out.println(actor);
		
		promptEnterKey();
		
		for(int i = 1; i < 10000; i++ ){
			System.out.println(i);
			StepResult step;
			try {
				step = actor.step(Method.Backward);
				System.out.println(actor);
				
				switch(step){
					case Success:
						System.out.println("Success!");
						actor.printExp();
						return;
					case NoPath:
						System.out.println("No Path Found");
						actor.printExp();
						return;
					default:
						break;
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
			//promptEnterKey();
		}*/
		
	}
	
	public static void runMap(Field field, Point start, Point finish, Method method){
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