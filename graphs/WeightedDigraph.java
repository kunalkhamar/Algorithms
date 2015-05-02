package graphs;

import java.util.LinkedList;
import java.util.List;

public class WeightedDigraph {
	private final int n;	// # of vertices
	private int m;			// # of edges
	List<DirectedEdge>[] nbr;
	
	@SuppressWarnings("unchecked")
	public WeightedDigraph(int V) {
		n = V;
		m = 0;
		nbr = (List<DirectedEdge>[]) new List[V];
		for (int i = 0; i < V; i++)
			nbr[i] = new LinkedList<DirectedEdge>();
	}
	
	public int V() { return n; }
	public int E() { return m; }
	
	public void addEdge(DirectedEdge e) {
		nbr[e.from()].add(e);
		m++;
	}
	
	public void addEdge(int to, int from, int weight) {
		addEdge(new DirectedEdge(to, from, weight));
	}
	
	public Iterable<DirectedEdge> nbr(int v) {
		return nbr[v];
	}
	
}
