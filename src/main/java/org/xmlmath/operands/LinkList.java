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

import java.util.List;

import org.xmlmath.x10.LinkListType;
import org.xmlmath.x10.ValueType;

/**
 * Convenience link to declarations of type <tt>List</tt>.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 18.apr.2006
 */
public class LinkList extends AbstractListValue {

	private Declaration listDeclaration = null;

	@Override
	public List<Object> getList() throws TypeCastException {
		
		try {
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>)listDeclaration.getValue();
			return list;
		} catch(ClassCastException cce) {
			throw new TypeCastException("Incompatible cast from " +
					cce.getMessage() + " to List.");
		}
	}
	
	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		LinkListType xml = (LinkListType)xmlObject;
		
		listDeclaration = Scope.getScope().getDeclaration(xml.getName());
		if(listDeclaration == null) {
			throw new ParseException("Reference attempted to undeclared " +
					"variable " + xml.getName());
		}
	}
}
