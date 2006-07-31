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

import org.apache.xmlbeans.XmlObject;
import org.xmlmath.x10.NumberType2;
import org.xmlmath.x10.SumType;
import org.xmlmath.x10.ValueType;

/**
 * Evaluates its operand in a loop (start - end) times and computes the sum of
 * all results. This operation represents the capital sigma. The iterator is
 * automatically declared as a long variable which can be referenced from
 * within the sum's expression.
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, Apr 12, 2006
 */
public class Sum extends AbstractNumberValue {

	/**
	 * Used to specify whether the list's elements must be interpreted as
	 * longs or doubles. Defaults to DOUBLE when omitted.
	 */
	private NumberType2.Enum type = NumberType2.DOUBLE;

	private AbstractNumberValue operand = null;
	private IndexDeclaration iterator = null;
	private AbstractLongValue start = null;
	private AbstractLongValue end = null;
	
	@Override
	public Number getNumber() {
		
		Number sum = null;
		final int end = this.end.getLong().intValue();

		switch(type.intValue()) {

			case NumberType2.INT_DOUBLE: {
				double _sum = 0;
				for(iterator.setIndex(start.getLong()); iterator.getIndex() <= end;
					iterator.increment()) {
					_sum += operand.getNumber().doubleValue();
				}
				sum = Double.valueOf(_sum);
				break;
			}
			
			case NumberType2.INT_LONG: {
				long _sum = 0;
				for(iterator.setIndex(start.getLong()); iterator.getIndex() <= end;
					iterator.increment()) {
					_sum += operand.getNumber().longValue();
				}
				sum = Long.valueOf(_sum);
				break;
			}
			
			default:
				throw new AssertionError("Unsupported data type: " +
						type.toString());
		}
		
		return sum;
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		SumType xml = (SumType)xmlObject;
		type = xml.getDatatype();
		Scope.getScope().setDeclaration(
				iterator = new IndexDeclaration(xml.getIterator()));
		
		if(xml.isSetStart() && xml.isSetEnd() && xml.isSetDo()) {
			start = (AbstractLongValue)AbstractValue.parse(
					xml.getStart().getAbstractLong());
			end = (AbstractLongValue)AbstractValue.parse(
					xml.getEnd().getAbstractLong());
			operand = (AbstractNumberValue)AbstractValue.parse(
					xml.getDo().getNumber());
			
		} else {
			start = (AbstractLongValue)AbstractValue.parse(
					xml.getAbstractLongArray(0));
			end = (AbstractLongValue)AbstractValue.parse(
					xml.getAbstractLongArray(1));
	
			/*
			 * This "manual" xpath query is necessary to select the last child
			 * element of the sum-loop. Using the getValue() method
			 * unfortunately selects the first child element (startIndex)
			 * because it is also a ValueType. Of course the xpath query could
			 * be more elegant.
			 */
			XmlObject[] elements = xml.selectPath("*");
			operand = (AbstractNumberValue)AbstractValue.parse(
					(ValueType)elements[elements.length - 1]);
		}
	}
}
