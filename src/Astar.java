import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Astar
{
	private Grid grid;
	private boolean dijkstra;
	
	public Astar(Grid g)
	{
		grid =g;
	}
	public Astar(Grid g,boolean dijkstra)
	{
		grid = g;
		this.dijkstra =dijkstra;
	}
	public int heuristic(Node a,Node b) //TODO find better heuristic
	{
		return heuristic(a.getCoord(),b.getCoord());
	}
	public int heuristic(Coord a,Coord b)
	{
		if(dijkstra)
			return 0;
		else
			return Math.abs(a.x - b.x) + Math.abs(a.y-b.y);
	}
	public int manhattanDistance(Coord a,Coord b)
	{
		return Math.abs(a.x - b.x) + Math.abs(a.y-b.y);
	}
	public ArrayList<Node> find(Node start,Node goal)
	{
		return find(start,goal,new HashMap<>(),new HashMap<>());
	}
	public ArrayList<Node> find(Node start, Node goal, HashMap<Node,Node> cameFrom, HashMap<Node,Integer> costSoFar)
	{
//		Node endTele=null; //TODO find path to nearest teleporter to end and to current position and see if it is worth it at all
//		for(Node n: grid.getTeleporters())
//		{
//			if(endTele==null || find(goal,n))
//		}
		PriorityQueue<NodeWrapper> frontier = new PriorityQueue<>();
		frontier.add(new NodeWrapper(start,0));
		cameFrom.put(start,start);
		costSoFar.put(start,0);
		
		while(!frontier.isEmpty())
		{
			Node current = frontier.poll().get();
			if(current.equals(goal))
				break;
			for(Node next: current.getNeighbors())
			{
				Integer newCost = costSoFar.get(current) + grid.getNode(next.getCoord()).getWeight();
				if(!costSoFar.containsKey(next) || newCost < costSoFar.get(next))
				{
					if(!costSoFar.containsKey(next))
						costSoFar.put(next,newCost);
					else
						costSoFar.replace(next,newCost);
					Integer priority = newCost + heuristic(next,goal);
					frontier.add(new NodeWrapper(next,priority));
					if(cameFrom.containsKey(next))
						cameFrom.replace(next,current);
					else
						cameFrom.put(next,current);
				}
			}
		}
		ArrayList<Node> reversed = new ArrayList<>();
		for(Node n = goal;!n.equals(start);n = cameFrom.get(n))
		{
			reversed.add(n);
		}
		ArrayList<Node> out = new ArrayList<>();
		reversed.forEach(p -> out.add(0,p));
		return out;
	}
}