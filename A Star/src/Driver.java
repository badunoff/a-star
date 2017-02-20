import java.awt.Point;
import java.util.Scanner;

public class Driver {
	public static final int DIMX = 20;
	public static final int DIMY = 20;
	
	public static void main(String[] args){ 
		
		
		Field field = new Field(DIMX, DIMY);
		field.random_percent_plain_field(70);
		
		Point start = field.randomValidLocation();
		Point finish = field.randomValidLocation(start);
		
		System.out.println(start);
		System.out.println(finish);
		System.out.println(Field.arrayToString(field.field));
		
		
		Actor actor = new Actor(field, DIMX, DIMY, start, finish);
		
		promptEnterKey();
		
		for(int i = 1; i < 10000; i++ ){
			System.out.println(i);
			boolean success = actor.step();
			System.out.println(actor);
			
			if(success){
				System.out.println("Success!");
				break;
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
		   scanner.close();
		}
}