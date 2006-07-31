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

import org.xmlmath.x10.LinkType;
import org.xmlmath.x10.ValueType;

/**
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 18.apr.2006
 */
public final class Link extends AbstractValue {

	private Declaration declaration = null;
	
	/**
	 * Evaluates and returns the value of the expression of the equally named
	 * declare-tag that was declared higher up in the expression tree.
	 */
	@Override
	public Object getValue() throws EvaluationException {
		return declaration.getValue();
	}

	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operants recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {
		
		LinkType xml = (LinkType)xmlObject;
		
		declaration = Scope.getScope().getDeclaration(xml.getName());
		if(declaration == null) {
			throw new ParseException("Reference attempted to undeclared " +
					"variable " + xml.getName());
		}
	}
}
