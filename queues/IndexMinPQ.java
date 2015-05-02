/**
 * Priority queue that supports changing priority
 * of keys after heapify operation was already performed
 * Restores heap order when priority is altered
 * requires: max number of elements is fixed
 * 
 * Used to improve runtime of:
 *   - Prim's algorithm for MST
 *   - Dijkstra's algorithm for single source SP
 * from O(E log E) with binary heap, to O(E log V)
 * 
 *  Based on implementation in Sedgewick, Wayne
 *  (http://algs4.cs.princeton.edu/code/)
 *  @author Kunal
 */

package queues;

public class IndexMinPQ<Key extends Comparable<Key>> {
	private int NMAX;
	private int N;
	private int[] pq; // index of key in heap position i
	private int[] qp; // heap position of key with index i
	private Key[] keys; // compare keys for heap order

	@SuppressWarnings("unchecked")
	public IndexMinPQ(int nmax) {
		NMAX = nmax;
		keys = (Key[]) new Comparable[nmax + 1];
		pq = new int[nmax + 1];
		qp = new int[nmax + 1];
		for (int i = 0; i <= nmax; i++)
			qp[i] = -1;
	}

	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N;
	}

	public boolean contains(int k) {
		if (k < 0 || k >= NMAX) return false;
		return qp[k] != -1;
	}

	public void insert(int k, Key key) {
		if (k < 0 || k >= NMAX) return;
		N++;
		qp[k] = N;
		pq[N] = k;
		keys[k] = key;
		swim(N);
	}

	public Key minKey() {
		return keys[pq[1]];
	}
	
	public Key keyOf(int k) {
		if (k < 0 || k >= NMAX) return null;
		return keys[k];
	}
	
	// returned index to be used with original array
	// pq's array will set element to null
	public int deleteMin() {
		int indexOfMin = pq[1];
		exch(1, N--);
		sink(1);
		qp[indexOfMin] = -1;
		keys[pq[N + 1]] = null;
		pq[N + 1] = -1;	// not necessary
		return indexOfMin;
	}
	
	private void sink(int k) {
		while (2 * k <= N) {
			int j = 2 * k;
			if (j < N && !less(j, j + 1)) j++;
			if (less(k, j)) return;
			exch(k, j);
			k = j;
		}
	}

	private void swim(int k) {
		while (k > 1 && greater(k / 2, k)) {
			exch(k, k / 2);
			k /= 2;
		}
	}

	private void exch(int i, int j) {
		int temp = pq[i];
		pq[i] = pq[j];
		pq[j] = temp;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}
	
	public void decreaseKey(int i, Key key) {
		keys[i] = key;
		swim(qp[i]);
	}
	
	private boolean less(int inda, int indb) {
		return keys[pq[inda]].compareTo(keys[pq[indb]]) < 0;
	}
	
	private boolean greater(int i, int j) {
		return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}

	// sample usage
	public static void main(String[] args) {
		int[] arr = { 4, 20, 5, 6, 8, 7, 10, 9 };

		IndexMinPQ<Integer> pq = new IndexMinPQ<Integer>(arr.length);

		for (int i = 0; i < arr.length; i++) {
			pq.insert(i, arr[i]);
		}

		while (!pq.isEmpty())
			System.out.print(arr[pq.deleteMin()] + " ");
	}
	
}
