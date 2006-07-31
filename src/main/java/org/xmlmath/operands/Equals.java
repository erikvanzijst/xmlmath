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

import org.xmlmath.x10.EqualsType;
import org.xmlmath.x10.ValueType;

/**
 * Compares two operands of any type. Also returns true when both operands are
 * nil, regardless of their type.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	1.0, 11.mar.2006
 */
public class Equals extends AbstractBooleanValue {

	private AbstractValue arg1 = null;
	private AbstractValue arg2 = null;
	
	@Override
	public Boolean getBoolean() {
		
		if(arg1.getValue() == null) {
			if(arg2.getValue() == null) {
				return true;
			} else {
				return false;
			}
		} else {
			return arg1.getValue().equals(arg2.getValue());
		}
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operants recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		EqualsType xml = (EqualsType)xmlObject;
		arg1 = AbstractValue.parse( xml.getValueArray(0) );
		arg2 = AbstractValue.parse( xml.getValueArray(1) );
	}
}
