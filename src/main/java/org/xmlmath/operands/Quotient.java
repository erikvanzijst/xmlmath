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

import org.xmlmath.x10.NumberType2;
import org.xmlmath.x10.QuotientType;
import org.xmlmath.x10.ValueType;

/**
 * Computes the quotient of two numbers.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 11.mar.2006
 */
public class Quotient extends AbstractNumberValue {

	/**
	 * Used to specify whether the list's elements must be interpreted as
	 * longs or doubles. Defaults to DOUBLE when omitted.
	 */
	private NumberType2.Enum type = NumberType2.DOUBLE;

	private AbstractNumberValue operand1 = null;
	private AbstractNumberValue operand2 = null;

	/**
	 * @return the result of <tt>operand1 / operand2</tt>.
	 * @exception DivideByZeroException when an integer division by zero is
	 * 	attempted.
	 */
	@Override
	public Number getNumber() throws EvaluationException, DivideByZeroException {

		Number sum = null;
		
		try {
			if(type == NumberType2.LONG) {
				sum = Long.valueOf(operand1.getNumber().longValue() /
					operand2.getNumber().longValue());
			} else if(type == NumberType2.DOUBLE) {
				sum = Double.valueOf(operand1.getNumber().doubleValue() /
					operand2.getNumber().doubleValue());
			} else {
				assert false : "Unsupported datatype.";
			}
		} catch(ArithmeticException ae) {
			throw new DivideByZeroException();
		}
		return sum;
	}

	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		QuotientType xml = (QuotientType)xmlObject;
		type = xml.getDatatype();
		operand1 = (AbstractNumberValue)AbstractValue.parse(xml.getNumberArray(0));
		operand2 = (AbstractNumberValue)AbstractValue.parse(xml.getNumberArray(1));
	}
}
