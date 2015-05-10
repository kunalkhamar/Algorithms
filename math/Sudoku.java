package math;

import java.util.HashSet;
import java.util.Set;

public class Sudoku {
	private char[][] puzzle;
	private char[][] solution;
	private boolean solved;
	private final int N = 9;

	public Sudoku(char[][] initial) {
		assert (initial.length == N);
		assert (initial[0].length == N);

		solved = false;
		puzzle = new char[N][N];
		solution = new char[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				puzzle[i][j] = initial[i][j];
				solution[i][j] = '0';
			}
		}
	}

	// Backtracking search
	public void solve() {
		if (solved)
			return;

		boolean foundEmpty = false;
		for (int i = 0; i < N && !foundEmpty; i++) {
			for (int j = 0; j < N && !foundEmpty; j++) {
				if (puzzle[i][j] == '0') {
					foundEmpty = true;

					Set<Character> usedUp = new HashSet<>();
					usedUp.addAll(getRowItems(puzzle, i));
					usedUp.addAll(getColItems(puzzle, j));
					usedUp.addAll(getSubGridItems(puzzle, i, j));

					for (char k = '1'; k <= '9'; k++) {
						// backtracking
						if (!usedUp.contains(k)) {
							puzzle[i][j] = k;
							solve();
							puzzle[i][j] = '0';
						}
					}
				}
			}
		}

		if (!foundEmpty) {
			solved = true;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					solution[i][j] = puzzle[i][j];
				}
			}
		}
	}

	private Set<Character> getRowItems(char[][] board, int r) {
		Set<Character> items = new HashSet<>();
		for (int j = 0; j < N; j++)
			if (board[r][j] != '0')
				items.add(board[r][j]);
		return items;
	}

	private Set<Character> getColItems(char[][] board, int c) {
		Set<Character> items = new HashSet<>();
		for (int i = 0; i < N; i++)
			if (board[i][c] != '0')
				items.add(board[i][c]);
		return items;
	}

	private Set<Character> getSubGridItems(char[][] board, int r, int c) {
		Set<Character> items = new HashSet<>();
		int iTop = (r / 3) * 3;
		int jTop = (c / 3) * 3;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				char cur = board[iTop + i][jTop + j];
				if (cur != '0')
					items.add(cur);
			}
		}
		return items;
	}

	public void printPuzzle() {
		printBoard(puzzle);
	}

	public void printSolution() {
		printBoard(solution);
	}

	private void printBoard(char[][] b) {
		for (int i = 0; i < N; i++)
			System.out.println(b[i]);
		System.out.println();
	}

	public char[][] getPuzzle() {
		return puzzle;
	}

	public char[][] getSolution() {
		return solution;
	}
	
	// sample client
	public static void main(String[] args) {
		char[] str = "000000000".toCharArray();
		char[][] arr = new char[][]{ str,str,str,str,str,str,str,str,str };
		Sudoku s = new Sudoku(arr);
		s.printPuzzle();
		s.solve();
		s.printSolution();
	}

}