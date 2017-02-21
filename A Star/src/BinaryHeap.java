import java.util.ArrayList;

public class BinaryHeap<T extends Comparable<T>>{
	private ArrayList<T> heap;
	
	public BinaryHeap(){
		this.heap = new ArrayList<T>();
	}
	
	public void push(T item){
		int item_index = heap.size();
		
		heap.add(item_index, item);
			
		while(item_index != 0){
			int parent_index = (item_index-1)/2; 
			
			if(heap.get(parent_index).compareTo(item) < 0){
				swap(item_index, parent_index);
				item_index = parent_index;
			}
			else{
				break;
			}
		}
	}
	
	public T pop(){
		if(heap.isEmpty()){
			return null;
		}
		else if(heap.size() == 1){
			return heap.remove(0);
		}
		
		T retval = heap.remove(0);
		
		heap.add(0, heap.remove(heap.size() - 1));
		
		int index = 0;
		
		while(index < heap.size() - 1)
		{
			int max_child_index;
			
			if(index * 2 + 1 >= heap.size()){
				return retval;
			}
			else if(index * 2 + 2 >= heap.size()){
				max_child_index = index * 2 + 1; 
			}
			else{
				max_child_index = (heap.get(index * 2 + 1).compareTo(heap.get(index * 2 + 2)) > 0) ? 
						index * 2 + 1 : 
						index * 2 + 2;
			}
								
			if(heap.get(index).compareTo(heap.get(max_child_index)) >= 0){
				break;
			}
			else{ //smaller than larger child
				swap(index, max_child_index);
				index = max_child_index;
			}
		}
		
		return retval;
	}
	
	private void swap(int index1, int index2){
		if(index1 < index2){
			index1 += index2;
			index2 = index1 - index2;
			index1 -= index2;
		} 
		
		T temp = heap.get(index1);
		heap.add(index2, temp); //add the shifting down node in the former place of max child
		heap.add(index1, heap.remove(index2 + 1)); //remove max child from list and put in place of shifting down node
		heap.remove(index1 + 1);
	}
	
	public String toString(){
		String retval = "[";
		for(int i = 0; i < heap.size(); i++){
			retval += " " + heap.get(i) + " ";
		}
		retval += "]";
		
		return retval;
	}
	
	public int size(){
		return heap.size();
	}
}