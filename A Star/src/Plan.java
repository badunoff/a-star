import java.awt.Point;
import java.util.LinkedList;


public class Plan {
	private PlanType type;
	private LinkedList<Point> list;
	
	public Plan(LinkedList<Point> list, PlanType type){
		this.list = list;
		this.type = type;
	}
	
	public Point remove(){
		switch(type){ 
			case QUEUE:
				return list.removeFirst();
			case STACK:
				return list.removeLast();
			default:
				return null;
		}
	}
}
