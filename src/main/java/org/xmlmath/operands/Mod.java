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

import org.xmlmath.x10.ModType;
import org.xmlmath.x10.ValueType;

/**
 * Computes <tt>integer % denominator</tt> where the denominator is the second
 * operand.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 12.mar.2006
 */
public class Mod extends AbstractLongValue {

	private AbstractLongValue integer = null;
	private AbstractLongValue denominator = null;

	/**
	 * Returns <tt>integer % denominator</tt>.
	 */
	@Override
	public Long getLong() {
		return integer.getLong() % denominator.getLong();
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		ModType xml = (ModType)xmlObject;
		integer = (AbstractLongValue)AbstractValue.parse( xml.getAbstractLongArray(0) );
		denominator = (AbstractLongValue)AbstractValue.parse( xml.getAbstractLongArray(1) );
	}
}
