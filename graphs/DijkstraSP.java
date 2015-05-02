/**
 * Dijkstra's algorithm for single source SP
 * Time: O(E log V)
 * 
 *  Based on implementation in Sedgewick, Wayne
 *  (http://algs4.cs.princeton.edu/code/)
 *  @author Kunal
 */

package graphs;

import queues.IndexMinPQ;

public class DijkstraSP {
	private DirectedEdge[] edgeTo;
	private int[] distTo;
	private IndexMinPQ<Integer> pq;
	
	public DijkstraSP(WeightedDigraph G, int s) {
		edgeTo = new DirectedEdge[G.V()];
		distTo = new int[G.V()];
		pq = new IndexMinPQ<Integer>(G.V());
		
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Integer.MAX_VALUE;
		distTo[s] = 0;
		
		pq.insert(s, 0);
		while (!pq.isEmpty()) {
			int v = pq.deleteMin();
			for (DirectedEdge e : G.nbr(v))
				relax(e);
		}
	}
	
	private void relax(DirectedEdge e) {
		int v = e.from(), w = e.to();
		if (distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			if (pq.contains(w)) 
				pq.decreaseKey(w, distTo[w]);
			else 
				pq.insert(w, distTo[w]);
		}
	}
	
	public int getDistTo(int v) {
		if (v < 0 || v >= distTo.length) return -1;
		return distTo[v];
	}
}
