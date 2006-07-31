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

import org.apache.xmlbeans.XmlObject;
import org.xmlmath.x10.ForType;
import org.xmlmath.x10.ValueType;

/**
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, Apr 12, 2006
 */
public class For extends AbstractListValue {

	private IndexDeclaration iterator = null;
	private AbstractLongValue from = null;
	private AbstractLongValue to = null;
	private AbstractValue operand = null;
	
	@Override
	public List<Object> getList() {
		
		List<Object> list = new ArrayList<Object>();

		int end = to.getLong().intValue();
		
		for(iterator.setIndex(from.getLong()); iterator.getIndex() < end;
			iterator.increment()) {
			
			list.add(operand.getValue());
		}
		return list;
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		ForType xml = (ForType)xmlObject;
		
		Scope.getScope().setDeclaration(
				iterator = new IndexDeclaration(xml.getIterator()));

		if(xml.isSetStart() && xml.isSetEnd() && xml.isSetDo()) {
			from = (AbstractLongValue)AbstractValue.parse(
					xml.getStart().getAbstractLong());
			to = (AbstractLongValue)AbstractValue.parse(
					xml.getEnd().getAbstractLong());
			operand = AbstractValue.parse(xml.getDo().getValue());

		} else {
			from = (AbstractLongValue)AbstractValue.parse(
					xml.getAbstractLongArray(0));
			to = (AbstractLongValue)AbstractValue.parse(
					xml.getAbstractLongArray(1));

			/*
			 * This "manual" xpath query is necessary to select the last child
			 * element of the for loop. Using the getValue() method
			 * unfortunately selects the first child element (startIndex)
			 * because it is also a ValueType. Of course the xpath query could
			 * be more elegant.
			 */
			XmlObject[] elements = xml.selectPath("*");
			operand = AbstractValue.parse(
					(ValueType)elements[elements.length - 1]);
		}
	}
}
