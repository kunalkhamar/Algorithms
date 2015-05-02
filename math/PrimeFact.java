/**
 * Prime factorization and arithmetic operations
 * in this representation
 * 
 * @author Kunal
 */

package math;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PrimeFact {

	// a prime factorization (p.f.) is of form
	// { (p_1 => count(p_1)), (p_2 => count(p_2)), ... , (p_k => count(p_k)) }
	// where p_1 ... p_k are the prime factors of n
	// n itself does not appear in the p.f.
	// 1 may or may not appear in the p.f.
	// requires: largest prime factor must be smaller than Integer.MAX_VALUE
	Map<Integer, Integer> fac;
	long number = -1;

	public PrimeFact(long n) {
		factorize(n);
		number = n;
	}

	public PrimeFact(final Map<Integer, Integer> init) {
		fac = new TreeMap<Integer, Integer>();
		for (Map.Entry<Integer, Integer> e : init.entrySet())
			fac.put(e.getKey(), e.getValue());
	}

	public PrimeFact(PrimeFact init) {
		this(init.fac);
		if (init.number > 0)
			number = init.number;
	}

	private void factorize(long n) {
		fac = new TreeMap<Integer, Integer>();
		if (n == 1) {
			fac.put(1, 1);
			return;
		}

		int max = (int) Math.ceil(Math.sqrt(n));
		for (int i = 2; i <= max; i++) {
			int occur = 0;
			while (n % i == 0) {
				n /= i;
				occur++;
			}
			if (occur > 0)
				fac.put(i, occur);
		}
		if (n > 1)
			fac.put((int) n, 1); // largest prime factor must be an int
	}

	public Map<Integer, Integer> getPrimeFactorization() {
		return fac;
	}

	public int getNumFactors() {
		int num = 1;
		for (Integer v : fac.values())
			num *= (v + 1);
		return num;
	}

	// beware, overflow
	public long getNumber() {
		if (number > 0)
			return number;

		long prod = 1;
		for (Map.Entry<Integer, Integer> e : fac.entrySet()) {
			prod *= (int) Math.pow(e.getKey(), e.getValue());
		}
		number = prod;
		return prod;
	}
	
	public PrimeFact multiplyPF(PrimeFact other) {
		Map<Integer, Integer> prod = new TreeMap<>();
		Set<Integer> primes = new HashSet<>();
		for (Integer i : fac.keySet())
			primes.add(i);
		for (Integer i : other.fac.keySet())
			primes.add(i);

		for (Integer p : primes) {
			Integer va = fac.get(p);
			Integer vb = other.fac.get(p);
			int tot = 0;
			if (va != null)
				tot += va;
			if (vb != null)
				tot += vb;
			if (tot > 0)
				prod.put(p, tot);
		}

		return new PrimeFact(prod);
	}

	public PrimeFact dividePF(PrimeFact divisor) {
		Map<Integer, Integer> negated = new TreeMap<>();
		for (Map.Entry<Integer, Integer> e : divisor.fac.entrySet()) {
			negated.put(e.getKey(), -e.getValue());
		}

		return multiplyPF(new PrimeFact(negated));
	}

}
