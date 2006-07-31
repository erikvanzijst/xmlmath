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

import org.xmlmath.x10.ListProductType;
import org.xmlmath.x10.NumberType2;
import org.xmlmath.x10.ValueType;

/**
 * Computes the product of all numbers in the given list.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 10.mar.2006
 */
public class ListProduct extends AbstractNumberValue {

	/**
	 * Used to specify whether the list's elements must be interpreted as
	 * longs or doubles. Defaults to DOUBLE when omitted.
	 */
	private NumberType2.Enum type = NumberType2.DOUBLE;

	/**
	 * This class expects a list whose elements are all of type
	 * <tt>java.lang.Number</tt>.
	 */
	private AbstractListValue list = null;

	/**
	 * @return	the product of all the list's items multiplied.
	 * @exception EvaluationException when an empty list is provided.
	 * @exception TypeCastException when the list is not homogeneous.
	 */
	@Override
	public Number getNumber() throws EvaluationException, TypeCastException {

		Number product = null;
		boolean first = true;

		try {
			if(type == NumberType2.LONG) {
	
				long intProduct = 0;
				for(Object item : list.getList()) {
					if(first) {
						intProduct = ((Number)item).longValue();
						first = false;
					} else {
						intProduct *= ((Number)item).longValue();
					}
				}
				product = Long.valueOf(intProduct);
			} else if(type == NumberType2.DOUBLE) {
				
				double doubleProduct = 0.0f;
				for(Object item : list.getList()) {
					if(first) {
						doubleProduct = ((Number)item).doubleValue();
						first = false;
					} else {
						doubleProduct *= ((Number)item).doubleValue();
					}
				}
				product = Double.valueOf(doubleProduct);
			} else {
				assert false : "Unsupported datatype.";
			}
		} catch(ClassCastException cce) {
			throw new TypeCastException("Incompatible cast. List items must " +
					"all be of type number.");
		}

		if(first) {
			throw new EvaluationException("Cannot compute the product of " +
					"an empty list.");
		} else {
			return product;
		}
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		ListProductType xml = (ListProductType)xmlObject;
		type = xml.getDatatype();
		list = (AbstractListValue)AbstractValue.parse( xml.getAbstractList() );
	}
}
