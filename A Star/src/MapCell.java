import java.awt.Point;


public class MapCell implements Comparable<MapCell>{
	private boolean open;
	private int search;
	private int g;
	private int h;
	private Point location;
	private Point cameFrom;
	
	public MapCell(Point pt){
		location = pt;
		this.open = true;
		this.search = 0;
		cameFrom = null;
	}
	
	public void setOpen(boolean open){
		this.open = open;
	}
	
	public boolean isOpen(){
		return open;
	}
	
	public String toString(){
		return (open) ? "   ." : "||||";
	}
	
	public int getG(){
		return g;
	}
	
	public void setG(int g){
		this.g = g;
	}
	
	public int getH()
	{
		return h;
	}
	
	public void setH(int h)
	{
		this.h = h;
	}
	
	public Point getCameFrom(){
		return cameFrom;
	}
	
	public void setCameFrom(Point pt){
		cameFrom = pt;
	}
	
	public Point getLocation(){
		return location;
	}
	
	public void setLocation(Point pt){
		location = pt;
	}
	
	public int getSearch(){
		return search;
	}
	
	public void setSearch(int search){
		this.search = search;
	}
	
	public int compareTo(MapCell other) throws ClassCastException
	{
		if (!(other instanceof MapCell))
		      throw new ClassCastException("A Person object expected.");
		int otherH = ((MapCell)other).getH();
		int otherG = ((MapCell)other).getG();
		if((this.h + this.g) > (otherH + otherG))
			return -1;
		if((this.h + this.g) < (otherH + otherG))
			return 1;
		if(this.g > otherG)
			return -1;
		return 1;
	}
}