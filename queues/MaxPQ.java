/**
 * Priority queue implemented with a max heap
 * Can be used as a min heap by supplying 
 * appropriate comparator (see sample client)
 * 
 * Based on implementation in Sedgewick, Wayne
 * (http://algs4.cs.princeton.edu/code/)
 */

package queues;

import java.util.Collections;
import java.util.Comparator;

public class MaxPQ<Key> {
	private Key[] pq; // index 1 to N
	private int N;
	private Comparator<Key> comp;

	@SuppressWarnings("unchecked")
	public MaxPQ(int capacity) {
		pq = (Key[]) new Object[capacity + 1];
		N = 0;
	}

	public MaxPQ() {
		this(1);
	}

	public MaxPQ(Comparator<Key> comp) {
		this(1);
		this.comp = comp;
	}

	public MaxPQ(int capacity, Comparator<Key> comp) {
		this(capacity);
		this.comp = comp;
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	public Key max() {
		if (isEmpty())
			return null;
		return pq[1];
	}

	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		if (capacity <= N)
			return;
		Key[] temp = (Key[]) new Object[capacity + 1];
		for (int i = 1; i <= N; i++)
			temp[i] = pq[i];
		pq = temp;
	}

	public void insert(Key x) {
		if (N >= pq.length - 1)
			resize(2 * pq.length);

		pq[++N] = x;
		swim(N);
	}

	public Key deleteMax() {
		if (isEmpty())
			return null;
		Key max = pq[1];
		exch(1, N);
		N--;
		sink(1);
		pq[N + 1] = null;
		if (N > 0 && (N <= (pq.length - 1) / 4))
			resize(pq.length / 2);
		return max;
	}

	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			exch(k, k / 2);
			k /= 2;
		}
	}

	private void sink(int k) {
		while (2 * k <= N) {
			int j = 2 * k;
			if (j < N && less(j, j + 1))
				j++;

			if (!less(k, j))
				break;
			exch(j, k);
			k = j;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean less(int inda, int indb) {
		if (comp != null)
			return comp.compare(pq[inda], pq[indb]) < 0;
		else
			return ((Comparable<Key>) pq[inda]).compareTo(pq[indb]) < 0;
	}

	private void exch(int inda, int indb) {
		Key temp = pq[inda];
		pq[inda] = pq[indb];
		pq[indb] = temp;
	}

	// CAUTION: heap order not reflected
	public String toString_() {
		if (isEmpty())
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append(pq[1]);
		for (int i = 2; i <= N; i++)
			sb.append(" " + pq[i]);
		return sb.toString();
	}

	// sample client
	public static void main(String[] args) {
		int[] arr = { 4, 20, 5, 6, 8, 7, 10, 9 };
		
		MaxPQ<Integer> maxPQ = new MaxPQ<>();

		Comparator<Integer> cmp = Collections.reverseOrder();
		MaxPQ<Integer> minPQ = new MaxPQ<>(cmp);

		for (int i = 0; i < arr.length; i++) {
			maxPQ.insert(arr[i]);
			minPQ.insert(arr[i]);
		}
		
		while (!maxPQ.isEmpty())
			System.out.print(maxPQ.deleteMax() + " ");
		System.out.println();
		while (!minPQ.isEmpty())
			System.out.print(minPQ.deleteMax() + " ");
	}
}
