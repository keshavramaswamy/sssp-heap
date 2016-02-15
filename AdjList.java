

import java.io.File;
//import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
//import java.util.Map.Entry;
//import java.util.Set;
import java.util.*;

class AdjListNode{
	//class for construction of adjacency list node
	int id;
 
	ArrayList<Integer> nodeId;
	ArrayList<Integer> dist;
	BinaryTrieClass	routerTable;

	public AdjListNode(int val) {
		//constructor
		this.id = val;
		nodeId=new ArrayList<Integer>();
		dist=new ArrayList<Integer>();
		routerTable = new BinaryTrieClass();
	}

	public Integer getId() {
		//getter method to return id
		return id;
	}

	public void setId(Integer val) {
		// getter method to set val
		this.id = val;
	}
	
	public void addNode(Integer id, Integer cost){
		//method to addnode	
		nodeId.add(id);
		dist.add(cost);
	}

	
	public ArrayList<Integer> getAdjacentNodes()
	{
		//getter method to return nodeid
		return nodeId;
	}
	
	public ArrayList<Integer> getDistances()
	{
		//getter method to get distance
		return dist;
	}
	public void insertrouterTable(String dest_node, Integer nextval) throws Exception { 
		//method to insert router table
		routerTable.nodeInsert(dest_node, nextval);
	}
	
	public BinaryTrieClass getrouterTable(){
		//method to return router table
		return routerTable;
	}
				
}

public class AdjList{
	//class for adjacency list
	AdjListNode source;
	AdjListNode dest;
	//Map<Integer, AdjListNode> vertices;
	AdjListNode[] vertices;
	int	num_v, num_e;
 	int number_of_vertices;
 	
	public AdjList(){
		
		source = null;
		dest = null;
		num_v = 0;
		num_e = 0;
	}
	
	public void createGraph (String filename) {
	
		//method to create adjacency list graph given file input as parameter
		
		int source;
		int destination;
		int weight;
		int count=1;
		try{
		File fileinput = new File(filename);
		if(fileinput.exists()) {
       		
            Scanner scan = new Scanner(fileinput);
            num_v = scan.nextInt();
            num_e = scan.nextInt();
        
            number_of_vertices = num_v;
            vertices=new AdjListNode[num_v];
            for(int i=0;i<num_v;i++)
            	vertices[i]=new AdjListNode(i);
            
            
            while (count <= num_e)          
            {
            	/* get source,destination and weight as input */
           	    source = scan.nextInt();            
                destination = scan.nextInt();
                weight = scan.nextInt();
                /* define edge */ 
                setEdge(source,destination,weight);         
                count++;
            }
        
            scan.close();
		}
		 else
    	 {
    		 System.out.println("File does not exist");
    	 }
		}catch(Exception e)
		{
			 System.out.println(e);
		}
	}	
	
	private void setEdge( int source,
			int destination, int weight) {
		//method to set edge using source, destination and weight

		vertices[source].addNode(destination,weight);
		vertices[destination].addNode(source,weight);
	}

	

	public void traverseNode(Integer val){
		//method to traverse node
		AdjListNode node = vertices[val];
		BinaryTrieClass table = node.getrouterTable();
		table.traverseTrie();	
	}
	
	public void addEntryRoutingTable(Integer node1, String dest, Integer next) throws Exception {
		//method to add entry to router table 
		AdjListNode node = vertices[node1];
		node.insertrouterTable(dest, next);
	}
	
	public int getNumVertices(){
		//method to add get number of vertices
		return num_v;
	}
	
	public Integer getSource() {
		//method to get source
		return source.getId();
	}
	
	public void setSource(Integer source_id){
		// method to set source
		AdjListNode node = vertices[source_id];
		
		if(node != null)
			this.source = node;
	}

	public Integer getDest() {
		//method to get destination
		return dest.getId();
	}

	public void setDest(Integer dest_id)
	{
		//method to set destination
		AdjListNode node = vertices[dest_id];
		try{
		if(node != null)
			this.dest = vertices[dest_id];
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
		
	public AdjListNode[] getVertices() {
		//method to return vertices 
		return vertices;
	}

	public String toString(){
		// method toString 
		String str = "";
		str += "Source: "+getSource()+"\n";
		str += "Destination: "+getDest()+"\n";

		
		for(int i=0;i<vertices.length;i++)
		{
			Integer key=i;
			AdjListNode value=vertices[key];
			str += "Vertex("+key+"):";
			ArrayList<Integer> nodeIds=value.getAdjacentNodes();
			ArrayList<Integer> dist=value.getDistances();
			for(int j=0;j<nodeIds.size();j++)
			{
			str += "Ady("+nodeIds.get(j)+", "+dist.get(j)+"), ";
			}
			
		}
		
		return str;
	}
	
	public ArrayList<Integer>  getAdjacent(Integer id){
		//method to get adjacent node
		AdjListNode node1 = vertices[id];
		return node1.getAdjacentNodes();
	}
	public ArrayList<Integer>  getDistances(Integer id){
		//method to get distances
		AdjListNode node1 = vertices[id];
		return node1.getDistances();
	}
	public Map.Entry<Integer, String> getNextHop(Integer val, String dest_id){
		//method to get next hop router
		AdjListNode node1 = vertices[val];
		return node1.routerTable.nodeFindlpm(dest_id);	
	}
	
	public void printGraph() {
		//method to print adjacency list graph
		for(Integer iter=0;iter<  vertices.length;iter++){
			AdjListNode theNode = vertices[iter];
			System.out.print(iter + "->");

			ArrayList<Integer> nodeIds=theNode.getAdjacentNodes();
			ArrayList<Integer> dist=theNode.getDistances();
			for(int j=0;j<nodeIds.size();j++)
			{
				System.out.print(nodeIds.get(j) + ":" + dist.get(j) + "->");
			}
			System.out.println();
			
		}
	}

	
	
	

}
