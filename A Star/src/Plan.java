import java.awt.Point;
import java.util.LinkedList;


public class Plan {
	private int expanded;
	private PlanType type;
	private LinkedList<Point> list;
	
	public Plan(LinkedList<Point> list, PlanType type, int exp){
		this.list = list;
		this.type = type;
		this.expanded = exp;
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
	
	public int getExp()
	{
		return expanded;
	}
	
	public void setExp(int expand)
	{
		this.expanded = expand;
	}
	
	public Point peekNextStep(){
		switch(type){ 
			case QUEUE:
				return list.peekFirst();
			case STACK:
				return list.peekLast();
			default:
				return null;
		}
	}
	
	
	public String toString(){
		String retval = "";
		switch(type){ 
			case QUEUE:
				for(int i = 0; i < list.size(); i++){
					retval += "Step " + (i + 1) + ": x = " + list.get(i).x + ", y = " + list.get(i).y + "\n";
				}
				break;
			case STACK:
				for(int i = list.size() - 1; i >= 0; i--){
					retval += "Step " + (list.size() - i) + ": x = " + list.get(i).x + ", y = " + list.get(i).y + "\n";
				}
				break;
			default:
				
		}
		
		return retval;
	}
	
	public boolean contains(Point point){
		return list.contains(point);
	}
}