import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Grid grid = new Grid();
		Scanner scanner = null;
		try
		{
			scanner = new Scanner(new File("src/input2.txt"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		ArrayList<String> lines = new ArrayList<>();
		while(scanner.hasNext())
		{
			lines.add(scanner.nextLine());
		}
		Node next = null;
		for(int i =0 ;i<lines.size();i++)
		{
			System.out.println("LINE: "+i);
			String[] strs = lines.get(i).split(" ");
			for(int j=0;j<strs.length;j++)
			{
				if(next!=null)
					next = next.add(new Node(strs[j],new Coord(i,j)));
				else
					next = grid.add(new Node(strs[j],new Coord(i,j)));
			}
		}
		grid.connectTeleporters();
		
		Coord s = new Coord(0,0);
		Coord e = new Coord(3,2);
		Node start = grid.getNode(s);
		Node end = grid.getNode(e);
		
		
		System.out.println("HERE");
	}
	public static void plus(Integer a)
	{
		a++;
	}
}
