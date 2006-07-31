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

import org.xmlmath.x10.UniqueType;
import org.xmlmath.x10.ValueType;

/**
 * Filters all duplicate items from the list and returns the result. The
 * ordering is the same as in the original list.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 11.mar.2006
 */
public class Unique extends AbstractListValue {

	private AbstractListValue list = null;
	
	/**
	 * @return	the same list as the given list, only with all duplicate items
	 * removed. The ordering is the same as in the original list.
	 */
	@Override
	public List<Object> getList() {
		
		List<Object> unique = new ArrayList<Object>();
		
		for (Object item : list.getList()) {
			if(!unique.contains(item)) {
				unique.add(item);
			}
		}
		return unique;
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		UniqueType xml = (UniqueType)xmlObject;
		list = (AbstractListValue)AbstractValue.parse( xml.getAbstractList() );
	}
}
