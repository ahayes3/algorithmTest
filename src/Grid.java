import java.util.ArrayList;

public class Grid
{
	private Node head;
	private ArrayList<Node> teles;
	public Grid()
	{
		head = null;
		teles = null;
	}
	public Node add(Node n)
	{
		if(head == null)
			setHead(n);
		else
			return head.add(n);
		return head;
	}
	public void setHead(Node n)
	{
		head=n;
	}
	public Node getNode(Coord c)
	{
		return head.getNode(c);
	}
	public Node getNode(int x,int y)
	{
		return head.getNode(x,y);
	}
	public Node bottomLeft()
	{
		return head.bottomLeft();
	}
	public int getWidth()
	{
		return head.getWidth();
	}
	public int getDepth()
	{
		return head.getDepth();
	}
	public void connectTeleporters()
	{
		head.connectTeleporters(teles);
	}
	public ArrayList<Node> getTeleporters()
	{
		return teles;
	}
}
