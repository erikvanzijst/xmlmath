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

import org.xmlmath.x10.FactType;
import org.xmlmath.x10.ValueType;

/**
 * Computes the factorial of a long with a simple, non-recursive algorithm.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	1.0, 12.mar.2006
 */
public class Factorial extends AbstractLongValue {

	private AbstractLongValue integer = null;

	/**
	 * @return !integer.
	 * @exception EvaluationException if a negative long is provided.
	 */
	@Override
	public Long getLong() throws EvaluationException {
		
		long result = integer.getLong();

		if(result > 0) {
			for(long nX = result - 1; nX > 0; result *= nX--);
		}
		else if(result == 0) {
			result = 1;
		} else {
			throw new EvaluationException("Cannot compute the factorial of " +
					"a negative integer.");
		}

		return result;
	}

	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		FactType xml = (FactType)xmlObject;
		integer = (AbstractLongValue)AbstractValue.parse( xml.getAbstractLong() );
	}
}
