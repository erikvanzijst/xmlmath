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

import org.xmlmath.x10.SubstrType;
import org.xmlmath.x10.ValueType;

/**
 * Returns a new string that is a substring of this string. The substring
 * begins at the specified beginIndex and extends to the character at index
 * endIndex - 1. Thus the length of the substring is endIndex-beginIndex.
 * <P>
 * When endIndex is omitted, it defaults to the end of the string.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 12.mar.2006
 */
public class SubString extends AbstractStringValue {

	private AbstractStringValue string = null;
	private AbstractLongValue beginIndex = null;
	private AbstractLongValue endIndex = null;

	/**
	 * @return a new string that is a substring of this string. The substring
	 * 	begins at the specified beginIndex and extends to the character at
	 * 	index endIndex - 1. Thus the length of the substring is
	 * 	endIndex-beginIndex.
	 * @exception EvaluationException when either of the indices lay beyond
	 * 	the string's boundaries.
	 */
	@Override
	public String getString() throws EvaluationException {

		try {
			if(endIndex != null) {
				return string.getString().substring(beginIndex.getLong().intValue(),
						endIndex.getLong().intValue());
			} else {
				return string.getString().substring(beginIndex.getLong().intValue());
			}
		} catch(IndexOutOfBoundsException e) {
			throw new EvaluationException(e.getMessage());
		}
	}

	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		SubstrType xml = (SubstrType)xmlObject;
		beginIndex = (AbstractLongValue)AbstractValue.parse(xml.getAbstractLongArray(0));
		if(xml.getAbstractLongList().size() == 2) {
			endIndex = (AbstractLongValue)AbstractValue.parse(xml.getAbstractLongArray(1));
		}
		string = (AbstractStringValue)AbstractValue.parse(xml.getAbstractString());
	}
}
