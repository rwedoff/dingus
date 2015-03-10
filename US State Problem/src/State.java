public class State
{
	public String state;
	public String neighbor;
	public State(String n)//Some data type
	{
		state =n;
		neighbor =null;
	}
	public State(String n, String array)
	{
		state=n;
		neighbor=array;
	}
	public String toString()
	{
		return state + neighbor;
	}
	
	
}
