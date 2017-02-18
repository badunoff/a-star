import java.awt.Point;
import java.io.Console;
import java.io.IOException;
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
		System.out.println(Field.arrayToString(field.field, start, start, finish));
		
		
		Actor actor = new Actor(field, DIMX, DIMY, start, finish);
		
		promptEnterKey();
		
		
		int enter = 13;
		
		int counter = 1;
		for(int i = 1; i < 10000; i++ ){
			System.out.println(i);
			boolean success = actor.dumbStep();
			System.out.println(actor);
			
			if(success){
				System.out.println("Success!");
				break;
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
