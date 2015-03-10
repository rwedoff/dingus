import java.util.Scanner;


public class Main {

	
	public static void main(String[] args) 
	{
		Tree tree = new Tree();
		System.out.println("Binary Search Tree: Ryan Wedoff\n\n");
		String input = "";
		Scanner scan = new Scanner(System.in);
		
		while(!(input.equals("n")))
		{
			Node newNode = tree.getInputFromUser();
			tree.insert(newNode.getData());
			System.out.println("Press y or n");
			
			input = scan.nextLine();
			
		}
		scan.close();
		
		tree.inorder(tree.root);
		
		

	}

}
