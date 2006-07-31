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

import org.xmlmath.x10.AbsType;
import org.xmlmath.x10.NumberType2;
import org.xmlmath.x10.ValueType;

/**
 * Computes absolute value of a number.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	1.0, 12.mar.2006
 */
public class Absolute extends AbstractNumberValue {

	/**
	 * Used to specify whether the list's elements must be interpreted as
	 * longs or doubles. Defaults to DOUBLE when omitted.
	 */
	private NumberType2.Enum type = NumberType2.DOUBLE;
	private AbstractNumberValue number = null;
	
	/**
	 * @return the absolute value of the given operand.
	 */
	@Override
	public Number getNumber() {
		
		Number result = null;
		
		if(type == NumberType2.LONG) {
			result =  Long.valueOf(Math.abs(
					number.getNumber().longValue()));
		} else if(type == NumberType2.DOUBLE) {
			result = Double.valueOf(Math.abs(
					number.getNumber().doubleValue()));
		} else {
			throw new AssertionError("Unsupported datatype: " + type);
		}
		return result;
	}

	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		AbsType xml = (AbsType)xmlObject;
		type = xml.getDatatype();
		number = (AbstractNumberValue)AbstractValue.parse(xml.getNumber());
	}
}
