/**
 * Data structure for non-negative integers with
 * arbitrary number of digits
 * Numbers represented with an ArrayList
 * 
 * @author Kunal
 */

package math;

import java.util.ArrayList;
import java.util.Stack;

public class BigInt {
	private ArrayList<Integer> digits;

	public static final BigInt ZERO = new BigInt("0");
	public static final BigInt ONE = new BigInt("1");

	public BigInt() {
		this(ZERO);
	}
	
	public BigInt(long n) {
		this(Long.toString(n));
	}

	public BigInt(String digits) {
		this.digits = new ArrayList<>();
		for (int i = 0, len = digits.length(); i < len; i++)
			this.digits.add(Character.getNumericValue(digits.charAt(i)));
	}

	@SuppressWarnings("unchecked")
	public BigInt(final ArrayList<Integer> digits) {
		this.digits = (ArrayList<Integer>) digits.clone();
	}

	@SuppressWarnings("unchecked")
	public BigInt(final BigInt b) {
		this.digits = (ArrayList<Integer>) b.digits.clone();
	}

	public int getDigit(int index) {
		if (index < 0 || index >= digits.size())
			return -1;
		return digits.get(index);
	}
	
	public ArrayList<Integer> getDigits() {
		return digits;
	}

	public boolean setDigit(int index, int element) {
		if (index < 0 || index >= digits.size() || !(element >= 0 && element <= 9))
			return false;
		digits.set(index, element);
		return true;
	}

	public int getNumDigits() {
		return digits.size();
	}

	public BigInt add(final BigInt that) {
		Stack<Integer> ans = new Stack<>();
		int inda = this.getNumDigits() - 1;
		int indb = that.getNumDigits() - 1;

		int rep = Math.max(inda, indb);
		int carry = 0;
		for (int i = rep; i >= 0; i--) {
			int sum = 0;
			if (inda >= 0)
				sum += this.getDigit(inda--);
			if (indb >= 0)
				sum += that.getDigit(indb--);
			sum += carry;
			ans.push(sum % 10);
			carry = sum / 10;
		}
		if (carry > 0)
			ans.push(carry);

		ArrayList<Integer> ret = new ArrayList<>();
		for (int i = ans.size() - 1; i >= 0; i--)
			ret.add(ans.get(i));
		if (ret.isEmpty())
			ret.add(0);

		return new BigInt(ret);
	}

	public BigInt multiply(long n) {
		return multiply(new BigInt(n));
	}
	
	public BigInt multiply(final BigInt that) {
		BigInt prod = new BigInt();

		for (int i = that.getNumDigits() - 1; i >= 0; i--) {
			int curDigit = that.getDigit(i);
			Stack<Integer> p = new Stack<>();

			int carry = 0;
			for (int j = this.getNumDigits() - 1; j >= 0; j--) {
				int dig = this.getDigit(j) * curDigit;
				dig += carry;
				p.push(dig % 10);
				carry = dig / 10;
			}
			if (carry > 0)
				p.push(carry);

			ArrayList<Integer> curProd = new ArrayList<>();
			for (int k = p.size() - 1; k >= 0; k--)
				curProd.add(p.get(k));
			if (curProd.isEmpty())
				curProd.add(0);

			// multiply by 10^(numDigits() - 1 - i)
			// if non-zero
			if (!(curProd.size() == 1 && curProd.get(0) == 0)) {
				for (int pow = 0, limit = that.getNumDigits() - 1 - i; pow < limit; pow++)
					curProd.add(0);
			}

			BigInt intermediate = new BigInt(curProd);
			prod = prod.add(intermediate);
		}

		return prod;
	}

	public String toString() {
		char[] arr = new char[this.getNumDigits()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = (char) ('0' + this.getDigit(i));
		return new String(arr);
	}

	// sample client
	public static void main(String[] args) {
		BigInt a = BigInt.ZERO;
		BigInt b = new BigInt(1);
		System.out.println(a.add(b));
		for (int i = 1; i <= 100; i++) {
			a = a.add(new BigInt("" + i));
			System.out.println(a);
		}
		System.out.println();
		
		System.out.println("'" + BigInt.ZERO + "'");
		System.out.println("'" + BigInt.ZERO.digits + "'");
		System.out.println();
		
		int xi = 12, yi = 15;
		System.out.println(xi * yi);
		BigInt x = new BigInt(xi);
		BigInt y = new BigInt(yi);
		System.out.print(x + " x " + y + " = ");
		System.out.println(x.multiply(y));
	}

}
