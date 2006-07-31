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

import org.xmlmath.x10.ListItemType;
import org.xmlmath.x10.ValueType;

/**
 * This operator selects the item at a specific index in a list. When an index
 * smaller than 0 or larger than the list-size is specified, an
 * <tt>EvaluationException</tt> is thrown.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 31.may.2006
 */
public class ListItem extends AbstractValue {

	private AbstractListValue list = null;
	private AbstractLongValue index = null;

	/**
	 * 
	 * @exception EvaluationException when the index lies outside the list's
	 * 	bounds.
	 */
	@Override
	public Object getValue() throws EvaluationException {

		try {
			return list.getList().get(index.getLong().intValue());
		} catch(IndexOutOfBoundsException e) {
			throw new EvaluationException("List index out of bounds: " +
					e.getMessage());
		}
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		ListItemType xml = (ListItemType)xmlObject;
		list = (AbstractListValue)AbstractValue.parse(xml.getAbstractList());
		index = (AbstractLongValue)AbstractValue.parse(xml.getAbstractLong());
	}
}
