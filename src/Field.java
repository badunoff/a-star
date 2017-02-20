import java.awt.Point;
import java.util.Arrays;
import java.util.Random;


public class Field {
	boolean[][] field;
	
	public Field(int x, int y){
		x = (x > 0) ? x : 1; 
		y = (y > 0) ? y : 1;
		
		field = new boolean[x][y];
	}
	
	public void set_random_field(){
		Random random = new Random();
		
		for(int x = 0; x < field.length; x++) {
	        for(int y = 0; y < field[x].length; y++){
	        	field[x][y] = random.nextBoolean();
	        }
	    }
	}
	
	public void random_percent_plain_field(int percentClear){
		Random random = new Random();
		
		for(int x = 0; x < field.length; x++) {
	        for(int y = 0; y < field[x].length; y++){
	        	int val = random.nextInt(100);
	        	
	        	if(val <= percentClear){
	        		field[x][y] = true;
	        	}
	        	else{
	        		field[x][y] = false;
	        	}	        	
	        }
	    }
	}
	
	public boolean check_loc(Point pt){
		if(pt.x < field.length && pt.x >= 0 && pt.y < field[pt.x].length && pt.y >= 0)
			return field[pt.x][pt.y];
		else
			return false;
	}
	
	
	
	
	public Point randomValidLocation(){
		Random random = new Random();
		
		int x = 0;
		int y = 0;
		
		boolean found = false;
		
		while(!found){
			x = random.nextInt(field.length);
			y = random.nextInt(field[0].length);
			
			if(field[x][y]){
				found = true;
			}
		}
		
		return new Point(x, y);
	}
	
	public Point randomValidLocation(Point notValid){
		Random random = new Random();
		
		int x = 0;
		int y = 0;
		
		boolean found = false;
		
		while(!found){
			x = random.nextInt(field.length);
			y = random.nextInt(field[0].length);
			
			if(field[x][y] && (notValid.x != x || notValid.y != y)){
				found = true;
			}
		}
		
		return new Point(x, y);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String toString(){
		return arrayToString(field);
	}
	
	public static String arrayToString(boolean[][] arr){
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
				if(arr[x][y]){
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
	
	public static String arrayToString(boolean[][] arr, Point obj){
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
				else if(arr[x][y]){
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
