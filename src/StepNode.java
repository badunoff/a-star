import java.awt.Point;


public class StepNode {

	public Point Location;
	public StepNode next;
	
	public StepNode(Point pt, StepNode next){
		Location = pt;
		this.next = next;
	}
}
