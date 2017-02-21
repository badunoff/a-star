import java.awt.Point;
import java.lang.reflect.Array;
import java.util.LinkedList;

public class PathComputer {
	
	public static Plan ComputePathForward(MapCell[][] map, Point start, Point end, int counter)
	{
		if(!Compute(map, start, end, counter))
			return null;
		LinkedList<Point> list = new LinkedList<Point>();
		MapCell current = map[end.x][end.y];
		Point curr = new Point(end.x, end.y);
		list.add(curr);
		while(current.getCameFrom() != map[start.x][start.y].getLocation())
		{
			current = map[current.getCameFrom().x][current.getCameFrom().y];
			curr = new Point(current.getLocation().x, current.getLocation().y);
			list.add(curr);
		}
		Plan plan = new Plan(list, PlanType.STACK);
		return plan;
	}
	
	public static Plan ComputePathBackwards(MapCell[][] map, Point end, Point start, int counter)
	{
		if(!Compute(map, start, end, counter))
			return null;
		LinkedList<Point> list = new LinkedList<Point>();
		MapCell current = map[start.x][start.y];
		Point curr = new Point(start.x, start.y);
		list.add(curr);
		while(current.getCameFrom() != map[end.x][end.y].getLocation())
		{
			current = map[current.getCameFrom().x][current.getCameFrom().y];
			curr = new Point(current.getLocation().x, current.getLocation().y);
			list.add(curr);
		}
		Plan plan = new Plan(list, PlanType.QUEUE);
		return plan;
	}
	
	public static boolean Compute(MapCell[][] map, Point start, Point end, int counter)
	{
		BinaryHeap<MapCell> openList = new BinaryHeap<MapCell>();
		map[start.x][start.y].setG(0);
		map[start.x][start.y].setSearch(counter);
		map[end.x][end.y].setG(Integer.MAX_VALUE);
		map[end.x][end.y].setSearch(counter);
		MapCell current = map[start.x][start.y];
		current.setH(Math.abs(start.x - end.x)+Math.abs(start.y - end.y));
		while(map[end.x][end.y].getG() > (current.getG() + current.getH()))
		{
			int currX = current.getLocation().x;
			int currY = current.getLocation().y;
			if(currX > 0 && map[currX-1][currY].isOpen()) //left
			{
				if(map[currX-1][currY].getSearch() < counter)
				{
					map[currX-1][currY].setSearch(counter);
					map[currX-1][currY].setG(Integer.MAX_VALUE);
					map[currX-1][currY].setH(Math.abs(map[currX-1][currY].getLocation().x - end.x)+Math.abs(map[currX-1][currY].getLocation().y - end.y));
				}
				if(map[currX-1][currY].getG() > (current.getG()+1))
				{
					map[currX-1][currY].setG(current.getG()+1);
					map[currX-1][currY].setCameFrom(current.getLocation());
					//if it's in the open list, remove it (ADD HERE)
					openList.push(map[currX-1][currY]);
				}
			}
				
			if(currY < Array.getLength(map) - 1 && map[currX][currY+1].isOpen()) //up
			{
				if(map[currX][currY+1].getSearch() < counter)
				{
					map[currX][currY+1].setSearch(counter);
					map[currX][currY+1].setG(Integer.MAX_VALUE);
					map[currX][currY+1].setH(Math.abs(map[currX][currY+1].getLocation().x - end.x)+Math.abs(map[currX][currY+1].getLocation().y - end.y));
				}
				if(map[currX][currY+1].getG() > (current.getG()+1))
				{
					map[currX][currY+1].setG(current.getG()+1);
					map[currX][currY+1].setCameFrom(current.getLocation());
					//if it's in the open list, remove it (ADD HERE)
					openList.push(map[currX][currY+1]);
				}
			}
				
			if(currX < Array.getLength(map) - 1 && map[currX+1][currY].isOpen()) //right
			{
				if(map[currX+1][currY].getSearch() < counter)
				{
					map[currX+1][currY].setSearch(counter);
					map[currX+1][currY].setG(Integer.MAX_VALUE);
					map[currX+1][currY].setH(Math.abs(map[currX+1][currY].getLocation().x - end.x)+Math.abs(map[currX+1][currY].getLocation().y - end.y));
				}
				if(map[currX+1][currY].getG() > (current.getG()+1))
				{
					map[currX+1][currY].setG(current.getG()+1);
					map[currX+1][currY].setCameFrom(current.getLocation());
					//if it's in the open list, remove it (ADD HERE)
					openList.push(map[currX+1][currY]);
				}
			}
				
			if(currY > 0 && map[currX][currY-1].isOpen()) //down
			{
				if(map[currX][currY-1].getSearch() < counter)
				{
					map[currX][currY-1].setSearch(counter);
					map[currX][currY-1].setG(Integer.MAX_VALUE);
					map[currX][currY-1].setH(Math.abs(map[currX][currY-1].getLocation().x - end.x)+Math.abs(map[currX][currY-1].getLocation().y - end.y));
				}
				if(map[currX][currY-1].getG() > (current.getG()+1))
				{
					map[currX][currY-1].setG(current.getG()+1);
					map[currX][currY-1].setCameFrom(current.getLocation());
					//if it's in the open list, remove it (ADD HERE)
					openList.push(map[currX][currY-1]);
				}
			}
			current = openList.pop();
			if(current == null){
				return false;
			}
			
		}
		if(openList.size() == 0)
			return false;
		return true;
	}
}