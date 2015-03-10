STATE OPTIMIZATION PROBLEM HW2 BY RYAN WEDOFF 9/21/14
	PROGRAM IN JAVA CREATED WITH ECLIPSE

How to run the Program, (Program is in Java):

Main method is in MAIN.  Press run, follow on screen instructions in the console.
MAKE A GRAPH FIRST! (By pressing 1 or 2, 1 is the default Adjacency List)

The default Adjacency List is a text file from the webiste:
http://homepage.cs.uiowa.edu/~kvaradar/fall2014/hw2.html


Approach:

	1.  I created the graph by scanning in the adjacencies list from a text file from online.
	2.  I then inserted each adjacency into an ArrayList of ArrrayLists.
		The first elemenet of each ArrayList is the host state.
	3.  I then created a BFS algorithm, that cycles through each state and its adjacencies.
	     it inserts the distance to each state from the START state into a distance array.
	4.  For finding the min/max/optimzation part, I checked every combination of states.
		I used a triple nested FOR loop to do this.  I call BSF for every state, and then 
		added that distance ArrayList into a distanceTotal ArrayList to see the combined
		shortest distances from 3 arbitrary states.  From distanceTotal, I found the max
		the largest distance and compared that number with every other combination.  It 
		was this number what determined the three optimal states.  If the that number was
		the lowest, then those three states would be printed out as the optimal states.
		For the default list, the maxium distance was 3, for AL CO and NY.
		There are multiple answers that are correct for this problem, but this was the first
		combination that came up with the lowest maxium distance.  The optimization algorithm 
		takes roughly 2.7 seconds my computer, times may vary on different hardware.
    