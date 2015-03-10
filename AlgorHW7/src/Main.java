import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static String target;
	public static String source;
	
	public static void main(String[] args) 
	{
		System.out.println("Place file into source file");
		System.out.println("Type in file name with .txt at the end");
		Scanner file = new Scanner(System.in);
		String fileName= file.nextLine();
		file.close();
		getData(fileName);
		ops(target,source);
		
	}
	
	
	
	
//Dynamic Algorithm that computes the min amount of Flip and Subs used.
	public static void ops(String t, String s)
	{
		
		int [] rec=new int[t.length()+1];
//Fills an array that determines how many operations it takes to transform the target into the string
		for( int i=1; i<t.length()+1; i++)
		{
			//System.out.println("\n\nLetter: " + t.charAt(i-1));
			int j []= detFlip(t,s,i-1);
			if(t.charAt(i-1) == s.charAt(i-1))
			{
				rec[i]=Math.min(rec[i-1], rec[j[0]]+(1+j[1]));
			}
			else
			{
				rec[i]=Math.min(rec[i-1]+1, rec[j[0]]+(1+j[1]));
			}
		}
		
		
//Back tracks the recursion to determine what operations were done to get the optimal solution
		String fin = t;
		int i=rec.length-1;
		while(i>0)
		{
			int j []=detFlip(t,s,i-1);		
			if(rec[i]==rec[i-1])
				i--;
			else if(rec[i]==rec[i-1])
				i--;
			else if(rec[i]==rec[j[0]]+1+j[1])
			{
				if(j[0] != i-1)
				{
				System.out.println("flip("+j[0]+","+(i-1)+")");
				fin = flip(fin,j[0],i-1);
				System.out.println(fin + "\n");
				
				}
				i = j[0]-2;
			}
		}
		for(int a=0; a<fin.length(); a++)
		{
			if(fin.charAt(a)!=s.charAt(a))
			{
				System.out.println("sub("+fin.charAt(a) +","+s.charAt(a)+")");
				fin = sub(fin,s.charAt(a),a);
				System.out.println(fin + "\n");
			}
		}
		
		System.out.println(fin + " = " + s);
		System.out.println("The Miniumum Number of Moves is: " + rec[rec.length-1]);
		
	}
	
	
//Determines the best flip from 0...pos, a given int from OPS.
	// Returns an int[] where int[0]= is the index to flip from and int[1]= the number of subs to be had for the best determined flip
	public static int[] detFlip(String t, String s, int pos)
	{
		int best=0, bestMatch=0, toBeMatched=0;
		int bestArray[] = new int[2];
			for(int i=0; i<=pos; i++)
			{
				String temp = flip(t,i,pos);
				
				int matched= 0;
				for(int j=0; j<=pos; j++)
				{
					if(temp.charAt(j)==s.charAt(j))
					{
						matched++;
					}
				
				}
				if(matched>=bestMatch)
				{
					bestMatch= matched;
					best = i;
				}
		
		}
			
//Determines how many letters are to be subed after the best flip is determined.
			String temp2 = null;
			if(pos > best)
			 temp2 = flip(t,best,pos);
			for(int j=best; j<pos; j++)
			{
				if(temp2.charAt(j) != s.charAt(j)){
					toBeMatched++;
				}
			}
			bestArray[0]=best;
			bestArray[1]= toBeMatched;
		return bestArray;
	
	}
	
	
//Takes a String, and two ints, and flips the characters within those two ints.
	public static String flip(String t, int i, int j)
	{
		StringBuilder targ = new StringBuilder(t);
		String temp = t.substring(i,j+1);
		String rev = new StringBuilder(temp).reverse().toString();
		String result = targ.replace(i, j+1, rev).toString();
		return result;
		
	}

//Takes a String, int and char, and replaces the char within the String at the given place.
	public static String sub(String t, char newLetter, int place)
	{
		StringBuilder replace = new StringBuilder(t);
		replace.setCharAt(place, newLetter);
		String subed = replace.toString();
		return subed;
	}
	
//Takes data from a text file, and lops off the numbers and spaces that are from the given format. 
//Returns 3 strings from the given text file in global variable TARGET and SOURCE
	public static void getData(String fileName)
	{
		String [] fill = new String[2];
		File file= new File("src/"+fileName);
		try{
			Scanner scan= new Scanner(file);
			while(scan.hasNextLine())
			{
				String test= scan.nextLine();
				
				if(fill[0]==null)
				{
					fill[0]=test;
				}
				else
				{
					fill[1]=test;
				}
								
			}
			scan.close();
		}
		catch(FileNotFoundException e)
		{
			
			e.printStackTrace();
		}
		source = fill[0].substring(3);
		target = fill[1].substring(3);
		System.out.println("Target: "+target);
		System.out.println("Source: "+source +"\n");
		
	}
}
