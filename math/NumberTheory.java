/**
 * Some number theoretic functions
 * that come up often
 * 
 * @author Kunal
 */

package math;

public class NumberTheory {
	
	// Euclidean GCD
	public static int gcd(int a, int b) {
		return (b == 0 ? a : gcd(b, a % b));
	}
	
	// compute n "choose" k
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

	// TODO sieve of Eratosthenes, permutations, primality testing
}
