/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * XMLMath - XML-based Expression Evaluator.
 * Copyright (C) 2006  Erik van Zijst
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * Erik van Zijst
 * De Weteringsbrugmolen 107
 * 1188 GX Amstelveen
 * The Netherlands
 *
 * erik@prutser.cx
 * http://xmlmath.org
 */
package org.xmlmath.operands;

import java.math.BigInteger;

/**
 * Fast factorial implementation. Taken from http://www.luschny.de/math/
 * <P>
 * This class is recommended for everyday use in a
 * pure Java environment. No supplementary library
 * is needed - only Java's Math BigInteger is used.
 * Moreover, it is simple and efficient and designed
 * as a class whose instances are potentially
 * executed by another thread.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	1.0, 12.mar.2006
 */
public class FastFactorial {

	private long N;
	  
	public BigInteger factorial(int n) {
		if (n < 0) {
			throw new ArithmeticException(
			"Factorial: n has to be >= 0, but was " + n);
		}
	
		if(n < 2) return BigInteger.ONE;
	 
		BigInteger p = BigInteger.ONE;
		BigInteger r = BigInteger.ONE;
		N = 1;
	  
		int log2n = 31 - Integer.numberOfLeadingZeros(n);
		int h = 0, shift = 0, high = 1;
	  
		while(h != n) {
			shift += h;
			h = n >>> log2n--;
			int len = high;
			high = (h & 1) == 1 ? h : h - 1;
			len = (high - len) / 2;
	
			if(len > 0) {
				p = multiply(p, product(len));
				r = multiply(r, p);
			}
		}
		return r.shiftLeft(shift);
	}
  
	private final BigInteger product(int n) {
		int m = n / 2;
		if( m == 0 ) return BigInteger.valueOf(N += 2);
		if( n == 2 ) return BigInteger.valueOf((N += 2) * (N += 2));
		return multiply(product(n - m), product(m));
	}

	static private final BigInteger multiply(BigInteger x, BigInteger y) {

		int h = Math.max(x.bitLength(), y.bitLength());
		if (h <= 20000) return x.multiply(y);
		h = (h / 2) + (h % 2);
		
		BigInteger aR = x.shiftRight(h);
		BigInteger aL = x.subtract(aR.shiftLeft(h));
		BigInteger bR = y.shiftRight(h);
		BigInteger bL = y.subtract(bR.shiftLeft(h));
		BigInteger abL = multiply(aL, bL);
		BigInteger abR = multiply(aR, bR);
		BigInteger abLR = multiply(aL.add(aR), bL.add(bR));
		  
		return abL.add(abLR.subtract(abL).subtract(abR).
				shiftLeft(h)).add(abR.shiftLeft(2*h));
	}
}
