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
import java.util.Iterator;
import java.util.List;

import org.xmlmath.x10.AbstractStringType;
import org.xmlmath.x10.StrcatType;
import org.xmlmath.x10.ValueType;

/**
 * Concatenates two or more individually declared strings, or all the elements
 * of a homogeneous string list.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 12.mar.2006
 */
public class StringCat extends AbstractStringValue {

	private AbstractStringValue[] strings = null;
	private AbstractListValue stringList = null;

	/**
	 * @return <tt>string1 + string2 + ...</tt>.
	 */
	@Override
	public String getString() {
		
		StringBuffer buf = new StringBuffer();
		
		if(stringList != null) {
			for (Iterator it = stringList.getList().iterator();
				it.hasNext(); buf.append((String)it.next()));
		} else {
			for (int nX = 0; nX < strings.length;
				buf.append(strings[nX++].getString()));
		}
		return buf.toString();
	}

	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		StrcatType xml = (StrcatType)xmlObject;
		
		if(xml.isSetAbstractList()) {
			stringList = (AbstractListValue)AbstractValue.parse(xml.getAbstractList());
		} else {
			List<AbstractStringValue> buf = new ArrayList<AbstractStringValue>();
			for (AbstractStringType string : xml.getAbstractStringList()) {
				buf.add( (AbstractStringValue)AbstractValue.parse(string) );
			}
			strings = buf.toArray(new AbstractStringValue[buf.size()]);
		}
	}
}
