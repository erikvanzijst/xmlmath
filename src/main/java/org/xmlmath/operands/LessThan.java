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

import org.xmlmath.x10.LessThanType;
import org.xmlmath.x10.ValueType;

public class LessThan extends AbstractBooleanValue {

	/**
	 * Whether the check should be <i>less than or equals to</i>, rather than
	 * just <i>less than</i>.
	 */
	private boolean inclusive = false;
	
	private AbstractNumberValue arg1 = null;
	private AbstractNumberValue arg2 = null;
	
	/**
	 * @return <tt>true</tt> if <tt>arg1</tt> is smaller than <tt>arg2</tt>.
	 * If inclusive is set, also returns <tt>true</tt> when <tt>arg1</tt> is
	 * smaller than, or equal to <tt>arg2</tt>.
	 */
	@Override
	public Boolean getBoolean() {
		
		Comparable<Comparable> c1 = (Comparable<Comparable>)arg1.getNumber();
		Comparable<Comparable> c2 = (Comparable<Comparable>)arg2.getNumber();

		if(inclusive) {
			return c1.compareTo(c2) <= 0;
		} else {
			return c1.compareTo(c2) < 0;
		}
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		LessThanType xml = (LessThanType)xmlObject;
		inclusive = xml.getInclusive();
		arg1 = (AbstractNumberValue)AbstractValue.parse( xml.getNumberArray(0) );
		arg2 = (AbstractNumberValue)AbstractValue.parse( xml.getNumberArray(1) );
	}
}
