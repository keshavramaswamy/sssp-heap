
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FibNode {
	//fibonacci heap node
     int degree = 0;       
     boolean childCut = false; 
     FibNode right, left, parent, child;  
     int data;     
     int minDistance; 

    FibNode(int val, int minD) {
    	//constructor 
        right = this;
        left = this;
        this.data = val;
        this.minDistance = minD;
    }
    
    public Integer getData() {
    	//getter method to return data
        return data;
    }
   
    public void setData(Integer val) {
        //method to set data
    	data = val;
    }

    public double getminDistance() {
    	//method to print the minimum distance
        return minDistance;
    }

    
}


public class FHeap {
	private Map<Integer, FibNode> nodes;
    private FibNode min_node;
    private int capacity ;

    public FHeap() {
    	//constructor
		min_node = null;
		capacity = 0;
		nodes = new HashMap<Integer, FibNode>();
	}
    
    
    public void insertNode(Integer value, int distance) {
    	//method to insert into heap by value
    	
        FibNode newNode = new FibNode(value, distance);
        
        nodes.put(value, newNode);
       
        
        min_node = pairwiseCombine(min_node, newNode);
        capacity ++;
        
    }
   
    public Integer removeMin() {
    	//method to remove minimum node of heap
    	FibNode temp = min_node;

        
        
        if (min_node.right == min_node) { 
            min_node = null; 
        }
        else {
            min_node.left.right = min_node.right;
            min_node.right.left = min_node.left;
            min_node = min_node.right; 
        }
        
        if (temp.child != null) {
            
            FibNode tempc = temp.child;
            
            do {
                tempc.parent = null;
                tempc = tempc.right;
            } while (tempc != temp.child);
        }

        min_node = pairwiseCombine(min_node, temp.child);

        if (min_node == null) 
        	{
        	return temp.getData();
        	}
        
        List<FibNode> list1 = new ArrayList<FibNode>();
        List<FibNode> list2 = new ArrayList<FibNode>();

        for (FibNode n = min_node; list2.isEmpty() || list2.get(0) != n; n = n.right)
            list2.add(n);

        for (FibNode n: list2) {    
            while (true) {   
                while (n.degree >= list1.size())
                	list1.add(null);

                if (list1.get(n.degree) == null) {
                	list1.set(n.degree, n);
                    break;
                }
                
                FibNode n2 = list1.get(n.degree);
                list1.set(n.degree, null); 

                FibNode min = (n2.minDistance < n.minDistance)? n2 : n;
                FibNode max = (n2.minDistance < n.minDistance)? n  : n2; 

                max.right.left = max.left;
                max.left.right = max.right;

                max.right = max;
                max.left = max;
                min.child = pairwiseCombine(min.child, max);
                
                max.parent = min;
                max.childCut = false;
                min.degree += 1;               
                n = min;
            }

            if (n.minDistance <= min_node.minDistance) min_node = n;
        }
        capacity -= 1;
        return temp.getData();
    }

    public void delete(FibNode value) {  
    	//method to delete node by value
        decreaseKey(value.getData(), Integer.MIN_VALUE);
        removeMin();
    }
    
    public void decreaseKey(Integer nodval, int distance) {
    	//method to perfrom decreaseKey operation
    	FibNode ncut = nodes.get(nodval);
    	if(ncut != null){
    		ncut.minDistance = distance;
    	       
            if (ncut.parent != null && ncut.minDistance <= ncut.parent.minDistance)
                cascadingCut(ncut);
          
            if (ncut.minDistance <= min_node.minDistance)
                min_node = ncut;    		
    	}
        
    }
    public Integer getMin() {
       //getter method to get the minimum node 
        return min_node.getData();
    }

    public boolean isEmpty() {
    	//method to check if empty
        return min_node == null;
    }

    private void cascadingCut(FibNode theNode) {
        //method to perform cascading cut 
    	theNode.childCut = false;
        
        if (theNode.parent == null) return;

        if (theNode.right != theNode) { 
        	theNode.right.left = theNode.left;
        	theNode.left.right = theNode.right;
        }
        
        if (theNode.parent.child == theNode) {
            
            if (theNode.right != theNode) {
            	theNode.parent.child = theNode.right;
            }
            else {
            	theNode.parent.child = null;
            }
        }

       
        theNode.parent.degree -= 1;

        theNode.left = theNode.right = theNode;
        min_node = pairwiseCombine(min_node, theNode);
        
        if (theNode.parent.childCut)
            cascadingCut(theNode.parent);
        else
        	theNode.parent.childCut = true;
        
        theNode.parent = null;
    }
    
 
    
    
    
    public int getSize() {
    	//getter method for capacity
        return capacity;
    }
   
    
    
    private FibNode pairwiseCombine(FibNode n1, FibNode n2) {
       //method to perform pairwise combine of fibonacci heap
        if (n1 == null && n2 == null) { 
            return null;
        }
        else if (n1 != null && n2 == null) { 
            return n1;
        }
        else if (n1 == null && n2 != null) { 
            return n2;
        }
        else { 
            FibNode oneNext = n1.right;
            n1.right = n2.right;
            n1.right.left = n1;
            n2.right = oneNext;
            n2.right.left = n2;
            
            return n1.minDistance < n2.minDistance? n1 : n2;
        }
    }
    
    

   
   
    
}