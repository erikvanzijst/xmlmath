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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.xmlmath.x10.IsDistinctType;
import org.xmlmath.x10.ValueType;

/**
 * Returns false if one or more list items are duplicates. True otherwise.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 11.mar.2006
 */
public class IsDistinct extends AbstractBooleanValue {

	private AbstractListValue list = null;

	@Override
	public Boolean getBoolean() {
		
		List<Object> org = list.getList();
		Set<Object> unique = new HashSet<Object>(org);
		
		return org.size() == unique.size();
	}

	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		IsDistinctType xml = (IsDistinctType)xmlObject;
		list = (AbstractListValue)AbstractValue.parse(xml.getAbstractList());
	}
}
