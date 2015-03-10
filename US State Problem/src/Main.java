import java.util.Scanner;



public class Main {

	
	public static void main(String[] args)  //Main Method
	{
		System.out.println("States Optimization Problem: By Ryan Wedoff, 9/21/14");
		System.out.println("\n\nMENU (SELECT YOUR CHOICE, do Option 1 or 2 first!)\n 1: Make Default Graph (From Website)\n 2: Make a New Graph (Type in Text File Path, [insert file into source folder] \n " +
				"3: Find the Optimal Locations to Reduce the Amount of States Crossed (A graph must be made first)\n 4: Run a search of distances for a select state \n 0: QUIT PROGRAM");
		Scanner scan = new Scanner(System.in);
		Scanner reader = new Scanner(System.in);
		int choice =scan.nextInt();
		Graph usa = new Graph();
		int i=0;
		while(i ==0)
		{
			switch(choice)
			{
				case 1: usa.makeGraph("Adj list.txt");
					System.out.println("GRAPH HAS BEEN MADE");
							break;
				
				case 2: System.out.println("ENTER GRAPH FILE PATH");
							String name = reader.nextLine();
							System.out.println(name + "Has been Created");
							usa.makeGraph(name);
							break;
				case 3: usa.optimal();
							break;
				case 4: System.out.println("Enter a state to search distances from!");
					String start = reader.nextLine();
					usa.BFS(start);
					usa.printAllDistances();
					break;
				case 0: i=1;
					System.exit(0);
					break;
				default:  System.out.println("That is not a choice");
				break;
					
			}
			System.out.println("\n\nMENU (SELECT YOUR CHOICE, do Option 1 or 2 first!)\n 1: Make Default Graph (From Website)\n 2: Make a New Graph (Type in Text File Path, [insert file into source folder] \n " +
					"3: Find the Optimal Locations to Reduce the Amount of States Crossed (A graph must be made first)\n 4: Run a search of distances for a select state \n 0: QUIT PROGRAM");
			choice =scan.nextInt();
		
		}
		
	   
	  

	}

}
