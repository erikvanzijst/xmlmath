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

import org.xmlmath.x10.Log10Type;
import org.xmlmath.x10.ValueType;

/**
 * Computes the common logarithm (base 10) of a double  value. If the argument
 * is positive infinity, then the result is positive infinity. If the argument
 * is positive zero or negative zero, then the result is negative infinity. If
 * the argument is equal to 10^n for integer n, then the result is n.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 14.apr.2006
 */
public class Log10 extends AbstractDoubleValue {

	private AbstractDoubleValue operand = null;

	/**
	 * @return common logarithm (base 10) of the operand.
	 */
	@Override
	public Double getDouble() {
		return Math.log10(operand.getDouble());
	}

	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		Log10Type xml = (Log10Type)xmlObject;
		operand = (AbstractDoubleValue)AbstractValue.parse(xml.getAbstractDouble());
	}
}
