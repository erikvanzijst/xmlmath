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

import org.xmlmath.x10.AbstractBooleanType;
import org.xmlmath.x10.AndType;
import org.xmlmath.x10.ValueType;

/**
 * Returns <tt>true</tt> if all operands are <tt>true</tt>.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	1.0, 12.mar.2006
 */
public class And extends AbstractBooleanValue {

	private AbstractBooleanValue[] booleans = null;

	/**
	 * @return the result of <tt>operand1 && operand2 && ...</tt>.
	 */
	@Override
	public Boolean getBoolean() {
		
		for (AbstractBooleanValue bool : booleans) {
			if(!bool.getBoolean()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		AndType xml = (AndType)xmlObject;
		List<AbstractBooleanValue> buf = new ArrayList<AbstractBooleanValue>();
		for (AbstractBooleanType bool : xml.getAbstractBooleanList()) {
			buf.add((AbstractBooleanValue)AbstractValue.parse(bool));
		}
		booleans = buf.toArray(new AbstractBooleanValue[buf.size()]);
	}
}
