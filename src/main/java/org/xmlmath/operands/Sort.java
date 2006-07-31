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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.xmlmath.x10.SortOrder;
import org.xmlmath.x10.SortType;
import org.xmlmath.x10.ValueType;

/**
 * Sorts the specified list into ascending order, according to the natural
 * ordering of its elements. All elements in the list must be of the same
 * type.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 12.mar.2006
 */
public class Sort extends AbstractListValue {

	private AbstractListValue list = null;
	private SortOrder.Enum order = SortOrder.ASCENDING;
	
	private final Comparator<Object> descender =
		new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
					return ((Comparable)o2).compareTo(o1);
				}
		};
	
	/**
	 * @return	the same list as the given list, only with all duplicate items
	 * 	removed. The ordering is the same as in the original list.
	 * @exception when the list is not homogeneous in nature.
	 */
	@Override
	public List<Object> getList() throws EvaluationException {

		// TODO: fix the generics issue properly
		List<Object> result = list.getList();
		try {
			Collections.sort(result, order == SortOrder.ASCENDING ?
					null : descender);
			return result;
		} catch(ClassCastException cce) {
			throw new EvaluationException("Cannot sort a heterogeneous list. " +
					"Make sure all elements are of the same type.");
		}
	}

	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		SortType xml = (SortType)xmlObject;
		order = xml.getOrder();
		list = (AbstractListValue)AbstractValue.parse( xml.getAbstractList() );
	}
}
