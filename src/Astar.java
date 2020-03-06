import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Astar
{
	private Grid grid;
	public Astar(Grid g)
	{
		grid =g;
	}
	public int heuristic(Node a,Node b) //TODO
	{
		return 0;
	}
	public int heuristic(Coord a,Coord b)
	{
		return heuristic(grid.getNode(a),grid.getNode(b));
	}
	public ArrayList<Node> find(Node start, Node goal, HashMap<Node,Node> cameFrom, HashMap<Node,Integer> costSoFar)
	{
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
