package oligo;

public class Node
{
	int parent;
	int overlap;

	public Node(int p, int o)
	{
		parent = p;
		overlap = o;
	}

	public int getParent()
	{
		return parent;
	}

	public int getOverlap()
	{
		return overlap;
	}
}