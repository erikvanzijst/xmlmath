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

import java.util.ArrayList;
import java.util.List;

import org.xmlmath.x10.AddType;
import org.xmlmath.x10.NumberType;
import org.xmlmath.x10.NumberType2;
import org.xmlmath.x10.ValueType;

/**
 * Computes the sum of two or more numbers.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 11.mar.2006
 */
public class Add extends AbstractNumberValue {

	/**
	 * Used to specify whether the list's elements must be interpreted as
	 * longs or doubles. Defaults to DOUBLE when omitted.
	 */
	private NumberType2.Enum type = NumberType2.DOUBLE;

	private AbstractNumberValue[] numbers = null;

	@Override
	public Number getNumber() {

		Number sum = null;
		
		if(type == NumberType2.LONG) {
			long result = 0;
			for(int nX = 0; nX < numbers.length; nX++) {
				result += numbers[nX].getNumber().longValue();
			}
			sum = Long.valueOf(result);
		} else if(type == NumberType2.DOUBLE) {
			double result = 0;
			for(int nX = 0; nX < numbers.length; nX++) {
				result += numbers[nX].getNumber().doubleValue();
			}
			sum = Double.valueOf(result);
		}
		return sum;
	}

	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		AddType xml = (AddType)xmlObject;
		type = xml.getDatatype();
		List<AbstractNumberValue> buf = new ArrayList<AbstractNumberValue>();
		for (NumberType string : xml.getNumberList()) {
			buf.add( (AbstractNumberValue)AbstractValue.parse(string) );
		}
		numbers = buf.toArray(new AbstractNumberValue[buf.size()]);
	}
}
