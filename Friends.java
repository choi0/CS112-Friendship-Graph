//Danny Choi
//Ronnie Mendoza

import java.io.File;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
//import java.util.StringTokenizer;
import java.util.TreeMap;
class Neighbor
{
    public int vertexNum;
    public Neighbor next;
    public Neighbor(int vnum, Neighbor nbr)
    {
            this.vertexNum = vnum;
            next = nbr;
    }
}
class vertex
{
    String name;
    public String school;
    Neighbor adjList;
    Vertex(String name,String schoolscool, Neighbor neighbors)
    {
            this.name = name;	
            this.school = schoolscool;
            this.adjList = neighbors;
    }
}
class Node
{
    
    public Vertex vertex;
    public Node next;
    public Node(Vertex vertex, Node next)
    {
            this.vertex = vertex;
            this.next = next;
    }
}
class Queue
{
    
    Node front, rear;
    int size;
    
    public Queue() {
            front = null;
            rear = null;
            size = 0;
    }
    public void enqueue(Vertex item) {
            Node temp = new Node(item, null);
            if (size == 0)
                    front = temp;
            else
                    rear.next = temp;
            
            rear = temp;
            size++;
    }
    public Vertex dequeue() 
    throws NoSuchElementException {
            if (front == null)
                    throw new NoSuchElementException();
            
            Vertex temp = front.vertex;
            front = front.next;
            size--;
            return temp;
    }
    public int size() {
            return size;
    }
    public boolean isEmpty() {
            return size == 0;
    }
    public void clear() {
            size = 0;
    }
}
class Graph {
	
	public Vertex[] adjLists;
	public HashMap<String,Integer> hashy=new HashMap(1000,0.75f);
	public Graph(String file) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(file));
		adjLists = new Vertex[sc.nextInt()];
		String undercaser;
		//System.out.println("adjLists size: "+adjLists.length);
		sc.nextLine();
		// read vertices
		for(int v=0; v < adjLists.length; v++)
		{
            String currline = sc.nextLine().toLowerCase();
            System.out.println("currline is: "+currline);
            //StringTokenizer toke = new StringTokenizer(currline,"|");
            //undercaser=toke.nextToken().toLowerCase();
            
            //
            String word=null;
            int x;
            for(x=0;currline.charAt(x)!='|';x++)
            {
            	if(word==null)
            		word=Character.toString(currline.charAt(x));
            	else
            		word = word+Character.toString(currline.charAt(x));
            }
            x++;
            System.out.println("word is: "+word);
            System.out.println("pos is: "+x);
            //System.out.println("substring is: "+currline.substring(x+2));
            if(currline.charAt(x)=='y')
            	adjLists[v]=new Vertex(word,currline.substring(x+2),null);
            else
            	adjLists[v]=new Vertex(word,null,null);
			hashy.put(adjLists[v].name, v);
            	
            //
            
			//adjLists[v] = new Vertex(undercaser, null, null);
			//hashy.put(adjLists[v].name, v);
           // undercaser=toke.nextToken().toLowerCase();
			//if(undercaser.equalsIgnoreCase("y"))
	       //     {
			//	undercaser=toke.nextToken().toLowerCase();
			//	adjLists[v]= new Vertex(adjLists[v].name,undercaser,null);
			//	}
			//else
			//	adjLists[v]= new Vertex(adjLists[v].name,null,null);
		}
		// read edges
		while (sc.hasNextLine())
		{
            String currline = sc.nextLine();
            System.out.println("currline2 is: "+currline);
            //
            String word1=null;
            int g;
            for(g=0;currline.charAt(g)!='|';g++)
            {
            	if(word1==null)
            		word1=Character.toString(currline.charAt(g));
            	else
            	word1 = word1+Character.toString(currline.charAt(g));
            }
            g++;
            String word2=currline.substring(g);
            System.out.println("word1 is: "+word1);
            System.out.println("word2 is: "+word2);
            
			int v1 = indexForName(word1);
			int v2 = indexForName(word2);
            
            //
          //  StringTokenizer toke = new StringTokenizer(currline,"|");
			// read vertex names and translate to vertex numbers
			//int v1 = indexForName(toke.nextToken());
		//	int v2 = indexForName(toke.nextToken());
			
			// add v2 to front of v1's adjacency list and
			// add v1 to front of v2's adjacency list
			adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList);			
			adjLists[v2].adjList = new Neighbor(v1, adjLists[v2].adjList);
		}
	}
	int indexForName(String name) {
		for (int v=0; v < adjLists.length; v++) {
			//System.out.println("comparing to "+adjLists[v].name);
			if (adjLists[v].name.equalsIgnoreCase(name)) {
				//System.out.println("name is: "+name+" ||| v is "+v);
				return v;
			}
		}
		return -1;
	}
	public void subgraph(String schoolname)
	{
		schoolname=schoolname.toLowerCase();
		if(schoolname==null)
				return;
		for(int x=0;x<adjLists.length;x++)
		{
			if(schoolname.equals(adjLists[x].school))
			{
				x=adjLists.length;
			}
			if(x==adjLists.length-1)
			{
				System.out.println("Please enter a school found in the graph");
				return;
			}
		}
		int count=0;
		//Vertex[] orig=adjLists;
		Vertex[] orig=new Vertex[adjLists.length];
		for(int x=0;x<adjLists.length;x++)
		{
			orig[x]=new Vertex(adjLists[x].name,adjLists[x].school,adjLists[x].adjList);
			//orig[x].name=adjLists[x].name;
			//orig[x].school=adjLists[x].school;
			//orig[x].adjList=adjLists[x].adjList;
			if(orig[x].school!=null)
			{
				if(orig[x].school.equals(schoolname))
				{
					count++;
				}
			}
		}
		Vertex[] schoolList=new Vertex[count];
		HashMap<String,Integer> schoolhash=new HashMap(100,0.75f);
		int y=0;
		for(int x=0;x<orig.length;x++)
		{
			if(orig[x].school!=null)
			{
				if(orig[x].school.equals(schoolname))
				{
					schoolList[y]=new Vertex(orig[x].name,schoolname,null);
					schoolhash.put(orig[x].name, y);
					y++;
				}
			}
		}
		for(int x=0;x<orig.length;x++)
		{
			if(orig[x].school!=null)
			{
				if(orig[x].school.equals(schoolname))
				{
					while(orig[x].adjList!=null)
					{
						if(orig[orig[x].adjList.vertexNum].school!=null)
						{
							if(orig[orig[x].adjList.vertexNum].school.equals(schoolname))
							{
								int target=schoolhash.get(orig[orig[x].adjList.vertexNum].name);
								int school=schoolhash.get(orig[x].name);
								schoolList[school].adjList=new Neighbor(target,schoolList[school].adjList);
							}
						}
						orig[x].adjList=orig[x].adjList.next;
					}
				}
			}
		}
		for(int x=0;x<schoolList.length;x++)
		{
			System.out.println(schoolList[x].name+"|y|"+schoolname);
		}
		for (int v=0; v < schoolList.length; v++)
		{
			for (Neighbor nbr=schoolList[v].adjList; nbr != null;nbr=nbr.next)
			{
				if(nbr.vertexNum<v)
				System.out.println(schoolList[v].name+"|"+schoolList[nbr.vertexNum].name);
			}
		}
	}
	public void shortestpath(String student1,String student2)
			//throws Exception
			{
				
				//System.out.println("Please enter the name of the first student: ");
				//String student1=IO.readString();
				//System.out.println("");
				//System.out.println("Please enter the name of the second student: ");
				//String student2=IO.readString();
				//System.out.println("");
				
				student1 = student1.toLowerCase();
				student2 = student2.toLowerCase();
				boolean s1c=false,s2c=false;
				for(int x=0;x<adjLists.length;x++)
				{
					if(!s1c||!s2c)
					{
						if(student1.equals(adjLists[x].name))
							s1c=true;
						if(student2.equals(adjLists[x].name))
							s2c=true;
					}
					if(x==adjLists.length-1&&(!s1c||!s2c))
					{
						System.out.println("Please enter students who are found in the graph");
						return;
					}
				}
				 int endIndex;
				 int ptr;
				 Vertex user; 
				 Queue x = new Queue();
				 Neighbor hold;
		         
		         int [] distance = new int[adjLists.length];
		         Vertex [] path = new Vertex[adjLists.length];
		         
		         for(int i = 0; i<adjLists.length; i++) {
		                 distance[i] = Integer.MAX_VALUE;
		         }
		         
		         endIndex = hashy.get(student2);
		         distance[endIndex] = 0;
		         x.enqueue(adjLists[endIndex]);
		         
		         while(!x.isEmpty()) {
		                 user = x.dequeue();
		                 hold = user.adjList;
		                 
		                 while(hold != null) {
		                         if(distance[hold.vertexNum] == Integer.MAX_VALUE){
		                        	 
			                         distance[hold.vertexNum] = distance[hashy.get(user.name)] + 1;
			                         path[hold.vertexNum] = user;
			                         x.enqueue(adjLists[hold.vertexNum]);
			                         
		                         }
		                        hold = hold.next;
		                 }
		         }
		         
		         if(distance[hashy.get(student1)] == Integer.MAX_VALUE || student1.equals(student2)){
		         //        throw new Exception("No path exists");
		         }
		         
		         ptr = hashy.get(student1);
		         
		         String namelist=null;
		         boolean ispath=false;
		         try{
		         while(!student2.equals(adjLists[ptr].name))
		         {
		        	 if(namelist==null)
		        		 namelist=adjLists[ptr].name+"---";
		        	 else
		        		 namelist=namelist+adjLists[ptr].name+"---";
		        	 //System.out.print(adjLists[ptr].name + "---");
		        	 ptr = hashy.get(path[ptr].name);
		         }
		         ispath=true;
		         }catch(Exception e){};
		         if(ispath)
		        	 System.out.println(namelist+student2);
		         else
		        	 System.out.println("There is no path between "+student1+" and "+student2+".");
		         
			}
	public void connectedislands(String schoolname)
	{
		schoolname=schoolname.toLowerCase();
		if(schoolname==null)
			return;
		for(int x=0;x<adjLists.length;x++)
		{
			if(schoolname.equals(adjLists[x].school))
			{
				x=adjLists.length;
			}
			if(x==adjLists.length-1)
			{
				System.out.println("Please enter a school found in the graph");
				return;
			}
		}
		Boolean[] visited=new Boolean[adjLists.length];
		Vertex currvert,holdervert;
		Neighbor neighborhunt;
		int cliquenumber=0;
		for(int x=0;x<adjLists.length;x++)
			visited[x]=false;
	
		for(int x=0;x<adjLists.length;x++)
		{
			if(visited[x]!=true)
			{
				if(schoolname.equals(adjLists[x].school))
				{
					Queue edgeq=new Queue();
					cliquenumber++;
	                System.out.println("Clique " + cliquenumber);
	                System.out.println(adjLists[x].name + "|y|" + schoolname);
					visited[x]=true;
					edgeq.enqueue(adjLists[x]);
					while(edgeq.isEmpty()!=true)
					{
						currvert=edgeq.dequeue();
						neighborhunt=currvert.adjList;
						while(neighborhunt!=null)
						{
							if(visited[neighborhunt.vertexNum]!=true)
							{
								holdervert=adjLists[neighborhunt.vertexNum];
								if(schoolname.equals(holdervert.school)!=true)
								{
									visited[neighborhunt.vertexNum]=true;
									continue;
								}
                                System.out.println(holdervert.name + "|y|" + schoolname);
								visited[neighborhunt.vertexNum]=true;
								edgeq.enqueue(holdervert);
								
							}
							neighborhunt=neighborhunt.next;
						}
					}
				}
				else
					visited[x]=true;
				
			}
			
			
			
		}
	}
	public void connectors()
	{
		
		
		boolean[] visited = new boolean[adjLists.length];
        int[] backNum = new int[adjLists.length];
        int[] dfsNum = new int[adjLists.length];
        int dfsNumCounter = 0, backNumCounter = 0; 
        
       
        TreeMap<Integer, Integer> connectors = new TreeMap<Integer, Integer>();
        
      
        for (int i = 0; i < adjLists.length; i++) {
               
                if (!visited[i]) {
                        connectors.put(i, 1);
                        
                   
                        
                        helpConnectors(adjLists[i], visited, dfsNum, backNum, dfsNumCounter, backNumCounter, connectors);
                }
        }
        
        if(connectors.containsValue(3) == false) {
                System.out.println("No connectors.");
                return;
        }
        
        System.out.print("The connectors are: ");
        Set<Entry<Integer, Integer>> connectorEntries = connectors.entrySet();
        Iterator<Entry<Integer, Integer>> it = connectorEntries.iterator();
        
        while (it.hasNext()) {
                Entry<Integer, Integer> entry = it.next();
                
                if (entry.getValue() == 3) {
                        System.out.print(adjLists[entry.getKey()].name + ", ");
                }
        }
        System.out.println();
}
private void helpConnectors(Vertex user, boolean[] visited, int[] dfsNum, int[] backNum, int dfsNumCounter, int backNumCounter, TreeMap<Integer, Integer> connectors) {
        
	
        
        int userIndex = hashy.get(user.name);
        visited[userIndex] = true;
        dfsNum[userIndex] = dfsNumCounter;  dfsNumCounter++;        
        backNum[userIndex] = backNumCounter;  backNumCounter++;
        
        
        Neighbor neighbor = user.adjList;
        while (neighbor != null) {
                
               
                if (!visited[neighbor.vertexNum]) {
                        Vertex next = adjLists[neighbor.vertexNum];
                        helpConnectors(next, visited, dfsNum, backNum, dfsNumCounter, backNumCounter, connectors);
                        
                       
                        if (dfsNum[userIndex] > backNum[neighbor.vertexNum]) {
                                backNum[userIndex] = Math.min(backNum[userIndex], backNum[neighbor.vertexNum]);
                        }
                        else {
                              
                                if (connectors.get(userIndex) == null) {
                                        
                                        connectors.put(userIndex, 3);
                                }
                                else if (connectors.get(userIndex) == 1) {
                                        
                                        connectors.remove(userIndex);
                                        connectors.put(userIndex, 2);
                                }
                                else if (connectors.get(userIndex) == 2) {
                                        
                                        connectors.remove(userIndex);
                                        connectors.put(userIndex, 3);
                                }
                        }
                }
                
                else {
                        backNum[userIndex] = Math.min(backNum[userIndex], dfsNum[neighbor.vertexNum]);
                }
                neighbor = neighbor.next;
        }
}
		
		
		
		
		
	
	public void print()
	{
		for (int v=0; v < adjLists.length; v++) {
			System.out.print(adjLists[v].name);
			for (Neighbor nbr=adjLists[v].adjList; nbr != null;nbr=nbr.next) {
				System.out.print(" --> " + adjLists[nbr.vertexNum].name);
			}
			System.out.println("");
		}
	}
}
		
		
		
		
	
public class Friends
{
	
	public static void main(String[] args) 
	throws IOException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter graph input file name: ");
		String file = sc.nextLine();
		Graph graph = new Graph(file);
		graph.print();
		System.out.println("");
		int choice=0;
		while (choice != 5)
		{
			System.out.println("Select a choice");
			System.out.println("(1) names of all students at a school");
			System.out.println("(2) shortest intro chain between two nikkas");
			System.out.println("(3) groups of cliques at a school");
			System.out.println("(4) connectors");
			System.out.println("(5) exit program");
			System.out.print("Please enter the corresponding number: ");
			choice=Integer.parseInt(sc.nextLine());
			while (choice < 1 || choice > 5)
			{
				System.out.println("Please enter valid input: ");
				choice = Integer.parseInt(sc.nextLine());
			}
			if(choice==1)
			{
				System.out.print("Please enter the name of the school: ");
				String school=sc.nextLine();
				System.out.println("");
				graph.subgraph(school);
				System.out.println("");
			}
			else if(choice==2)
			{
				System.out.print("Please enter the name of the first student: ");
				String student1=sc.nextLine();
				System.out.print("Please enter the name of the second student: ");
				String student2=sc.nextLine();
				System.out.println("");
				graph.shortestpath(student1,student2);
				System.out.println("");
			}
			else if(choice==3)
			{
				System.out.print("Please enter the name of the school: ");
				String school=sc.nextLine();
				System.out.println("");
				graph.connectedislands(school);
				System.out.println("");
			}
			else if(choice==4)
			{
				graph.connectors();
			}
			else if(choice==5)
			{
				return;
			}
		}		
	}
}
