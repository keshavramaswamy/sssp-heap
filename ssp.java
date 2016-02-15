


import java.util.Map;




public class ssp {
	
	public static void main(String args[]){
		
		AdjList adjList = new AdjList();
		// get sourcenode and destination node as commandline input */
		int source_node = Integer.parseInt(args[1]);
		int dest_node   = Integer.parseInt(args[2]);
		
		//create adjacency list with filename as input
		adjList.createGraph(args[0]);
		
		adjList.setDest(dest_node);
		
		// perform djikstra's algorithm
		DjikstrasAlg alg = new DjikstrasAlg();
		Map.Entry<Integer[], Integer[]> res = alg.invoke(adjList, source_node);
		
		
		
		// calculate shortest path to standard output stream
		String finalpath = printssp(res,adjList);
		
		// print path from source to destination
		System.out.println(finalpath);
				
	}

	private static String printssp(Map.Entry<Integer[], Integer[]> result,AdjList adjList) {
		//method to print single shortest path algorithm
		Integer[] listVertices = result.getValue();
		Integer NodeId = listVertices[adjList.getDest()];
		String path = adjList.getDest() + "";
		
		while(NodeId != -1){
			path = NodeId + " "+ path;
			NodeId = listVertices[NodeId];
		}
	Integer[] listmin = result.getKey();	
	int dest = adjList.getDest();
	System.out.println(listmin[dest]);
	return path;
	}


}
