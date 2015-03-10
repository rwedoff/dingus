
public class Node
{
	private String data;
	private Node left;
	private Node right;
	
	public Node()
	{
		right = null;
		left = null;
		data = null;
	}
	public Node(String input)
	{
		data = input;
		left = null;
		right = null;
	}
	
	public Node getLeft()
	{
		return left;
	}
	public Node getRight()
	{
		return right;
	}
	public String getData()
	{
		return data;
	}
	public void setLeft(Node nodeToAdd)
	{
		left = nodeToAdd;
	}
	public void setRight(Node nodeToAdd)
	{
		right = nodeToAdd;
	}
	
}
