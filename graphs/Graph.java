/**
 * Undirected graph, adjacency list representation
 * 
 * Based on implementation in Sedgewick, Wayne
 * (http://algs4.cs.princeton.edu/code/)
 * @author Kunal
 */

package graphs;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
	private int n; // number of vertices
	private int m; // number of edges
	private List<Integer>[] nbr; // neighbors of each vertex
	
	@SuppressWarnings("unchecked")
	public Graph(int n) {
		this.n = n;
		nbr = (List<Integer>[]) new List[n];
		for (int i = 0; i < n; i++)
			nbr[i] = new LinkedList<Integer>();
	}
	
	public void addEdge(int v, int w) {
		nbr[v].add(w);
		nbr[w].add(v);
		m++;
	}
	
	public int V() { return n; }
	public int E() { return m; }
	
	public Iterable<Integer> nbr(int v) {
		return nbr[v];
	}
	
	public boolean isPathBetween(int v, int w) {
		return bfs(v, w);
	}
	
	@SuppressWarnings("unused")
	private boolean dfs(int v, int w) {
		boolean[] marked = new boolean[n];
		dfs(v, w, marked);
		return marked[w];
	}
	
	private void dfs(int v, int w, boolean[] marked) {
		marked[v] = true;
		
		for (int p : nbr[v]) {
			if (!marked[w]) {
				dfs(p, w, marked);
			}
		}
	}
	
	private boolean bfs(int v, int w) {
		boolean[] marked = new boolean[n];
		Queue<Integer> queue = new ArrayDeque<Integer>();
		queue.offer(v);
		marked[v] = true;
		
		while (!queue.isEmpty() && !marked[w]) {
			int next = queue.poll();

			if (!marked[next]) {
				for (int p : nbr[next]) {
					queue.offer(p);
				}
				marked[next] = true;
			}
		}
		
		return marked[w];
	}
}
