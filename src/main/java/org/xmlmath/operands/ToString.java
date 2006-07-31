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

import org.xmlmath.x10.ToStringType;
import org.xmlmath.x10.ValueType;

/**
 * Returns a string representation of its operand. When the operand is a list,
 * the string representation consists of a list of the operands elements in
 * their original order, enclosed in square brackets ("[]"). Adjacent elements
 * are separated by the characters ", " (comma and space). The list elements
 * are converted to strings with this operation.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 14.apr.2006
 */
public final class ToString extends AbstractStringValue {

	private AbstractValue operand = null;
	
	/**
	 * @return <tt>null</tt>.
	 */
	@Override
	public String getString() throws EvaluationException {
		return String.valueOf(operand.getValue());
	}

	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		ToStringType xml = (ToStringType)xmlObject;
		operand = AbstractValue.parse(xml.getValue());
	}
}
