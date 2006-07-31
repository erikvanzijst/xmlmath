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

import org.xmlmath.x10.ListType;
import org.xmlmath.x10.ValueType;

/**
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0
 */
public class ListValue extends AbstractListValue {

	private List<AbstractValue> arguments = new ArrayList<AbstractValue>();
	
	public void addItem(AbstractValue item) {
		arguments.add(item);
	}
	
	/**
	 * @return	a list that contains all entries whose value is not
	 * <tt>null</tt>.
	 */
	@Override
	public List<Object> getList() {
		
		List<Object> list = new ArrayList<Object>();
		
		for (AbstractValue arg : arguments) {
			if(arg.getValue() != null) {
				list.add(arg.getValue());
			}
		}
		return list;
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		ListType xml = (ListType)xmlObject;
		
		for(ValueType item : xml.getValueList()) {
			arguments.add( AbstractValue.parse(item) );
		}
	}
}
