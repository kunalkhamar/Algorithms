/*
 * Longest Palindromic Subsequence (LPS)
 * The length of an LPS of a string is equal to the length of  
 * a Longest Common Subsequence (LCS) of the string and its reverse
 * 
 * This is a solution to the following DWITE problem
 * http://dwite.ca/questions/tattarrattat.html
 */

package dynamicProgramming;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LongestPalindromicSubsequence {
	
	public static void main(String[] args) throws IOException {
		Scanner fin = new Scanner(new File("DATA5.txt"));
		PrintWriter fout = new PrintWriter("OUT5.txt");

		for (int ind = 0; ind < 5; ind++) {
			String str = fin.nextLine().trim();
			fout.println(LPSLength(str));
		}

		fin.close();
		fout.close();
		System.exit(0);
	}
	
	/**
	 * Length of the LPS
	 */
	public static int LPSLength(String str) {
		String rev = new StringBuffer(str).reverse().toString();
		return LCSLength(str, rev);
	}

	/**
	 * Length of LCS of two strings using DP
	 * Time: O(m^2)
	 * Space could be improved to O(m) by only 
	 * keeping 2 rows of the table at any time
	 */
	public static int LCSLength(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				if (str1.charAt(i - 1) == str2.charAt(j - 1))
					dp[i][j] = dp[i - 1][j - 1] + 1;
				else
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
			}
		}

		return dp[len1][len2];
	}
	
}
