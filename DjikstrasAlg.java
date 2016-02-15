

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class DjikstrasAlg {

	
public Entry<Integer[], Integer[]> invoke(AdjList adjList, int src){	
	// method to invoke the djikstra's algorithm
		
		FHeap fibheap = new FHeap();
		adjList.setSource(src);
		int num = adjList.getNumVertices();
		int iter =0;
		Map.Entry<Integer[],Integer[]> result;
		
		Integer[] Listcost = new Integer[adjList.getNumVertices()];
		Integer[] ListVertices = new Integer[adjList.getNumVertices()];
		
		
		while(iter<num){
			Integer nodeLabel = new Integer(iter);
			if (!nodeLabel.equals(adjList.getSource())){
				Listcost[iter] = Integer.MAX_VALUE;
				
			}else
				Listcost[iter] = 0;
			ListVertices[iter] = -1;
			fibheap.insertNode(nodeLabel, Listcost[iter]);
			iter++;
		}
		
		while(!fibheap.isEmpty()){
			Integer min = fibheap.removeMin();
			
			
			ArrayList<Integer> adjacentVertices=adjList.getAdjacent(min);
			ArrayList<Integer> dist=adjList.getDistances(min);
			for(int i=0;i<adjacentVertices.size();i++)
			{
				Integer Vert = adjacentVertices.get(i);
			    Integer distance = dist.get(i);
			    int cost = Listcost[min] + distance;
			    if(cost < Listcost[Vert]){
			    	Listcost[Vert] = cost;
			    	ListVertices[Vert] = min;
			    	fibheap.decreaseKey(Vert, cost);
			}
			
			}
		}
		
		result = new AbstractMap.SimpleEntry<Integer[], Integer[]>(Listcost, ListVertices);
		return result;
	}


	
	
	
}
