/**
 * Some number theoretic functions
 * that come up often
 * 
 * @author Kunal
 */

package math;

public class NumberTheory {

	/*
	 * Euclidean GCD algorithm
	 */
	public static int gcd(int a, int b) {
		return (b == 0 ? a : gcd(b, a % b));
	}

	/*
	 * Compute n "permute" k nPr = n! / k!
	 */
	public static long permutations(int n, int k) {
		// n * (n-1) * ... * (n-k+1)
		PrimeFact perm = new PrimeFact(1);
		for (int i = n; i > k; i--)
			perm = perm.multiplyPF(new PrimeFact(i));

		return perm.getNumber();
	}

	/*
	 * Compute n "choose" k nCr = n! / (k! * (n-k)!)
	 */
	public static long combinations(int n, int k) {
		k = Math.max(k, n - k);

		// n * (n-1) * ... * (n-k+1)
		PrimeFact numerator = new PrimeFact(1);
		for (int i = n; i > k; i--)
			numerator = numerator.multiplyPF(new PrimeFact(i));

		// (n-k)!
		PrimeFact denominator = new PrimeFact(1);
		for (int i = 1; i <= n - k; i++)
			denominator = denominator.multiplyPF(new PrimeFact(i));

		PrimeFact answer = numerator.dividePF(denominator);
		return answer.getNumber();
	}

	// TODO sieve of Eratosthenes, primality testing
}
