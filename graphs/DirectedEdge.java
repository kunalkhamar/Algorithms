package graphs;

public class DirectedEdge {
	private final int to, from;
	private final int weight;
	
	public DirectedEdge(int from, int to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public int from() { return from; }
	public int to() { return to; }
	public int weight() { return weight; }
	
}
