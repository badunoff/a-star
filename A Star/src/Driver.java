import java.awt.Point;
import java.util.Scanner;

public class Driver {
	public static final int DIMX = 20;
	public static final int DIMY = 20;
	
	public static void main(String[] args){ 
		
		
		Field field = new Field(DIMX, DIMY);
		field.random_percent_plain_field(65);
		
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
				step = actor.step(Method.Adaptive);
				System.out.println(actor);
				
				switch(step){
					case Success:
						System.out.println("Success!");
						return;
					case NoPath:
						System.out.println("No Path Found");
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
		}
		
	}
	
	public static void promptEnterKey(){
		   System.out.println("Press \"ENTER\" to continue...");
		   Scanner scanner = new Scanner(System.in);
		   scanner.nextLine();
		}
}