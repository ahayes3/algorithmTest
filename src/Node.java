import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

public class Node
{
	private short weight;
	private Coord coord;
	private Node up,right,left,down,tele;
	private int teleNum;
	
	public Node(String s,Coord coord)
	{
		this.coord = coord;
		if (s.charAt(0) >= 49 && s.charAt(0) <= 57)
		{
			weight = Short.parseShort(s);
			tele = null;
			teleNum=-1;
		}
		else if (s.charAt(0) == 116)
		{
			weight = 0;
			teleNum = Integer.parseInt(s.substring(1));
		}
		else if (s.charAt(0) == 102)
		{
			weight = -1;
			tele = null;
			teleNum = -1;
		}
		up=left=right=down=null;
	}
	public Node(short weight, Coord coord)
	{
		this.weight = weight;
		this.coord = coord;
		up=left=right=down=null;
		tele = null;
		teleNum = -1;
	}
	public int getWidth()
	{
		if(right == null)
			return 1;
		else
			return 1+right.getWidth();
	}
	public int getDepth()
	{
		if(down == null)
			return 1;
		else
			return 1+down.getDepth();
	}
	public int getWeight()
	{
		return weight;
	}
	public Coord getCoord()
	{
		return coord;
	}
	private int getTeleNum()
	{
		return teleNum;
	}
	public Node getTele()
	{
		return tele;
	}
	public ArrayList<Node> getNeighbors()
	{
		ArrayList<Node> out = new ArrayList<>();
		out.add(up);
		out.add(right);
		out.add(down);
		out.add(left);
		out.add(tele);
		return out;
	}
	public Node add(Node n)
	{
		if(n.coord.x == 0 && this.coord.x > 0)
			return getNode(0,coord.y).add(n);
		else if (n.coord.y > this.coord.y && this.down != null)
			return this.down.add(n);
		else if (n.coord.x > this.coord.x && this.right != null)
			return this.right.add(n);
		else if (n.coord.y > this.coord.y)
		{
			down = n;
			n.up = this;
			if(getNode(n.coord.x+1,n.coord.y)!=null)
			{
				getNode(n.coord.x+1,n.coord.y).left = n;
				n.right= getNode(n.coord.x+1,n.coord.y);
			}
			return n;
		}
		else if(n.coord.x > this.coord.x)
		{
			right = n;
			n.left=this;
			if(getNode(n.coord.x,n.coord.y-1)!=null)
			{
				getNode(n.coord.x,n.coord.y-1).down = n;
				n.up = getNode(n.coord.x,n.coord.y-1);
			}
			return n;
		}
		return null;
	}
	
	public Node getNode(Coord c)
	{
		return getNode(c.x, c.y);
	}
	public Node getNode(int x,int y)
	{
		if(this.coord.x == x && this.coord.y == y)
			return this;
		else
		{
			Node current = this;
			do
			{
				if(x < current.coord.x)
					current = current.left;
				else if(y < current.coord.y)
					current = current.up;
				else if(x > current.coord.x)
					current = current.right;
				else if(y > current.coord.y)
					current  = current.down;
			}while(current !=null && (!(current.coord.x == x && current.coord.y == y)));
			return current;
		}
	}
	public Node bottomLeft()
	{
		if(down != null)
			return down.bottomLeft();
		else
			return this;
	}
	public void connectTeleporters(ArrayList<Node> teles)
	{
		teles = new ArrayList<>();
		connectTeleporters(new ArrayList<>(),teles);
	}
	public void connectTeleporters(ArrayList<Node> found,ArrayList<Node> teles)
	{
		if(teleNum >0)
		{
			Node a=null;
			for(Node n:found)
			{
				if(n.teleNum == teleNum)
					a = n;
			}
			if(a!=null)
				connect(a,this);
			else
				found.add(this);
			//Optional<Node> a = found.stream().filter(p->p.teleNum == teleNum).findFirst();
			//a.ifPresent(node -> connect(this, node));
		}
		
		if(right == null && down ==null)
		{
			teles.add(this);
			return;
		}
		else if(right == null)
		{
			Node n = getNode(0,this.getCoord().y+1);
			teles.add(n);
			n.connectTeleporters(found,teles);
		}
		else
		{
			teles.add(right);
			right.connectTeleporters(found);
		}
	}
	public static void connect(Node a,Node b)
	{
		a.tele = b;
		b.tele = a;
	}
	@Override
	public String toString()
	{
		return "Weight: "+weight+" "+coord.toString();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		try
		{
			Node n = (Node) obj;
			if(n.coord.equals(coord))
				return true;
		}
		catch(ClassCastException e)
		{
			return false;
		}
		return false;
	}
}
