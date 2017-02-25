import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ReadAndWriteBoard {
	public static void write(boolean[][] map, Point start, Point finish, String name){

		StringBuilder builder = new StringBuilder();
		builder.append(map.length + "," + map[0].length + "\n");
		builder.append(start.x + "," + start.y + "," + finish.x + "," + finish.y + "\n");
		
		for(int i = 0; i < map.length; i++)//for each row
		{
			for(int j = 0; j < map.length; j++)//for each column
			{
				builder.append(((map[i][j] == true) ? 0 : 1) + "");//append to the output string
				if(j < map.length - 1)//if this is not the last row element
					builder.append(",");//then add comma (if you don't like commas you can use spaces)
			}
			
			builder.append("\n");//append new line at the end of the row
		}
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(name + ".csv"));
			writer.write(builder.toString());//save the string representation of the board
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Field read(String name, Point start, Point finish){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(name + ".csv"));
			String line = "";
			String[] cols;
			
			line = reader.readLine();
			cols = line.split(",");
			boolean[][] map = new boolean[Integer.parseInt(cols[0])][Integer.parseInt(cols[1])];
			
			line = reader.readLine();
			cols = line.split(",");
			
			start.setLocation(Integer.parseInt(cols[0]), Integer.parseInt(cols[1]));
			finish.setLocation(Integer.parseInt(cols[2]), Integer.parseInt(cols[3]));
			
			
			int row = 0;
			while((line = reader.readLine()) != null)
			{
			   cols = line.split(","); //note that if you have used space as separator you have to split on " "
			   int col = 0;
			   for(String  c : cols)
			   {
			      map[row][col] = (Integer.parseInt(c) == 0) ? true : false;
			      col++;
			   }
			   row++;
			}
			
			reader.close();
			
			return new Field(map);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
