import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Collections;


public class Graph 
{
	 private static ArrayList<ArrayList> list = new ArrayList<ArrayList>();
	 private static ArrayList<String> states;
	 private HashMap<String, Integer> stateMap;
 	 private HashMap<String, Boolean>visitedMap = new HashMap<String, Boolean>();
 	 private HashMap<String, Integer>distance = new HashMap<String, Integer>();
	 private ArrayList<Integer>distanceTotal = new ArrayList<Integer>();
		
    public Graph()
    {
    	
    }
    	
   
   public void printGraph() //Prints out the adjacency list
   {
	  for(int i=0; i<list.size(); i++)
	  {
		  System.out.print(list.get(i).get(0)+ ": ");
		  for(int j=1; j<list.get(i).size(); j++)
		  {
			System.out.print(list.get(i).get(j) + " ");
			
		  }
		  System.out.println();	  
	  }	
	  
   }

public void printAllDistances()
{
	for(int i=0; i<list.size(); i++)
	  {
		  System.out.print(list.get(i).get(0)+ ": ");
		  System.out.print("distance " + distanceTotal.get(i) + " ");
		  System.out.println();	  
		  //distance.get(list.get(i).get(0))
	  }	

}



   public void makeGraph(String fileName) //Creates a graph based on a Text file in the Workspace file.
   {
	    
	   try {
	         File file = new File(fileName); //Takes in the text file, adjacency data
	         Scanner scanner = new Scanner(file); //Reads file
	        String preState=""; //Creates a variable of the previous state
	   
	         while (scanner.hasNextLine()) //Cycles through text file
	         {                
	             String line = scanner.nextLine();
	             String array[] = line.split(" "); //Splits the state and neighbor info
	            if(array.length == 2)
	            {
	            
	            	if(!(array[0].equals(preState))) //Searches for new states and then adds them to the list
		            	{               		 	
	            		 	states= new ArrayList<String>();
	            		 	states.add(array[0]);
	            		 	states.add(array[1]);
		            		preState=array[0];
		            		list.add(states);
		            		
		            	}
	           
	            	 else if(array[0].equals(preState)) //If not a new state, then add only the neighbor to the adj list.
	            	 {
	            		 states.add(array[1]);
	            	 }
	            }
	           
	         }
	         scanner.close();
	     } catch (FileNotFoundException e)
	     {
	    	 System.out.println("That file isn't found (Check source folder)");
	         e.printStackTrace();
	        
	     }
	   
	   stateMap = new HashMap<String, Integer>();  //ADDS A NUMBER TO EVERY STATE starting from the top state in the list at 0
	   for(int p=0; p<list.size(); p++)
		{
			stateMap.put((String) list.get(p).get(0),p);
		}
	   
	   
	   for(int i=0; i<list.size();i++ ) //Makes the graph symmetrical, AL > FL and then FL > AL
	   {
		   for(int j=0; j<list.get(i).size(); j++)
		   {
			   if(list.get(i).contains((String) list.get(i).get(j))) 
			   {
				   if(!checkAdded((String) list.get(i).get(j))) //Checks if the Adj is added to the host state list, TRUE: Add state to list, FALSE:  Add the adjs.
				   {
					   states= new ArrayList<String>();
					   states.add((String) list.get(i).get(j));
					   states.add((String) list.get(i).get(0));
					   list.add(states);
					   stateMap.put((String) list.get(i).get(j), list.size()-1);
			   
				   }
				   else
				   {
					   
					   if(!list.get(i).get(0).equals((String) list.get(i).get(j)))
						   list.get(stateMap.get((String) list.get(i).get(j))).add(list.get(i).get(0));
				   }
			   }
		   }
	   }
		  
	   for(int p=0; p<list.size(); p++) //Sets all states as unchecked
		{
			for(int k=0; k<list.get(p).size(); k++)
			{
					visitedMap.put((String) list.get(p).get(k), false);
   			}
		}   
			   
		  
	   
   }

   public boolean checkAdded(String n) //Checks if a state has been added to the list of host states
   {
   		for(int i=0; i<list.size(); i++)
   		{
   			if(list.get(i).get(0).equals(n))
   			{
   				return true;
   			}
   	}
   		return false;
   }
   
   

	public void BFS(String start) //Searches for all states from the host state and fills up  an array of distances from the host state
	{	
		 Queue<String> q = new LinkedList<String>();
		 q.add(start);
		 distance.put(start, 0);
		 int callCount=0;
		 visitedMap.put(start,true);
		
	      while( !q.isEmpty() )
	      {
	    	  String child;
		      String n;
	    	  n = q.peek();
	    	  
	    	// System.out.println(q); //Prints out the Queue q
	    	while(stateMap.get(n)==null) //Checks to see if n was apart of the original state list, if not, then continue through the adjacency list.
	    	{
	    		if(q.peek()==null) //Checks to see if q is empty within the loop
	    			break;
	    		else
	    		{
	    			q.remove();	
	    			n=q.peek();
	    		}
	    	}
	    	if(q.peek()==null) //Checks to see if q is empty within the loop
    			break;
	        
	    	
	    	child = getNextNeighbor(stateMap.get(n));  //Gets the next unvisited node from the adjacency list.  If there exists one, then it returns that node, if not, then it return ""

	         if ( !child.equals("")) //Adds the next child into q, else it removes the head of q
	         {
	        	visitedMap.put(child, true);	         
	            String next = child;
	            q.add(next);
	            distance.put(next, distance.get(n)+1);
	            	            
	         }
	         else
	         {
	        	q.remove();
	         }
	      }
	      
	      for(int e=0; e<distance.size(); e++)
	      {
	    	  if(callCount==0)  //Checks to see if distanceTotal is empty
	    	  {
	    		  distanceTotal.add(distance.get(list.get(e).get(0)));
	    		
	    	  }
	    	   if(distance.get(list.get(e).get(0)) < distanceTotal.get(e))
	    	  {
	    		  distanceTotal.set(e,distance.get(list.get(e).get(0)));
	       	  }
	      }
	      
	      
	      for(int i=0; i<list.size(); i++) //Resets the algorithm for another call
	    	  for(int j=0; j<list.get(i).size(); j++)
	    	  {
	    		  visitedMap.put((String) list.get(i).get(j), false);
	    	  }
	      callCount ++;
	}
	
	
	
	
public  String getNextNeighbor(int n) //Gets the next unvisited neighbor from the adjacency list.  If there exists one, then it returns that neighbor, if not, then it return ""
{
	
	 for(int l=0; l<list.get(n).size(); l++)
     {

		if(visitedMap.get(list.get(n).get(l))==false) //Checks to see if the neighbor is unvisited and returns that neighbor
		 {
			return (String) list.get(n).get(l);
		 }
		 
     }	
	 		return (""); //If all neighbors visited, then returns ""
	      
}



public int getMaxDist() //Finds the maximum distances from a host state
{
	int max=0;
	for(int p=0; p<list.size(); p++)
	{
		if(max < distanceTotal.get(p))
		{
			max = distanceTotal.get(p);
		}
	}
	distanceTotal.clear(); //Resets the distance array for another call of BFS
	return max;
}

public void optimal() //Finds the optimal locations that minimizes the state boundaries to be crossed 
{
	int min=50;
	String d="";
	String e="";
	String f="";
	for(int a =0; a<list.size(); a++)
		for(int b=a+1; b<list.size();b++)
			for(int c=b+1; c<list.size(); c++)
			{
				if(!list.get(a).get(0).equals(list.get(b).get(0)) &&  !list.get(b).get(0).equals(list.get(c).get(0)) && !list.get(a).get(0).equals(list.get(c).get(0)))
				{
					BFS((String) list.get(a).get(0));
					BFS((String) list.get(b).get(0));
					BFS((String) list.get(c).get(0));
					int temp= getMaxDist();
					if(temp < min)
					{
						d=(String) list.get(a).get(0);
						e=(String) list.get(b).get(0);
						f=(String) list.get(c).get(0);
						min=temp;
					}
					
				}
				
			}
	System.out.println("Maximum Distance: " + min +" \nState 1: " + d + " \nState 2: " + e + " \nState 3: " +f );
	
	
}



}
