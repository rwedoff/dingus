
import java.util.Scanner;


public class Tree
{
	public Node root;
	
	public Tree()
	{
		root = null;
	}
	public Node getInputFromUser()
	{
		
		System.out.println("Enter the node value you wish to enter in BST: ");
		Scanner scan3 = new Scanner(System.in);
		String nodeValue =  scan3.nextLine();
		Node newNode = new Node(nodeValue);
		
		return newNode;
	}
	
	public void inorder(Node r)
	{
		if(r != null)
		{
			inorder(r.getLeft());
			System.out.println(r.getData());
			inorder(r.getRight());
		}
	}
	
	
	
	 public void insert(String data)
     {
         root = insert(root, data);
     }
	
	private Node insert(Node node, String data)
    {
        if (node == null)
            node = new Node(data);
        else
        {
            if (data.compareTo( node.getData()) > 0)
                node.setLeft(insert(node.getLeft(), data));
            else
                node.setRight(insert(node.getRight(), data));
        }
        return node;
    }
	
}
