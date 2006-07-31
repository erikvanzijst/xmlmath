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

import org.xmlmath.CacheHandle;
import org.xmlmath.ExpressionContext;
import org.xmlmath.x10.ConstScope;
import org.xmlmath.x10.DeclareType;

/**
 * Declarations are used to give a particular sub-expression a name so that it
 * can be referenced from elements deeper in the expression tree. The
 * expression inside a declaration is not evaluated at runtime, unless it is
 * explicitly referenced by the link-tag. It is evaluated each time it is
 * linked.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 11.apr.2006
 */
public class DeclarationImpl implements Declaration {

	private final String name;
	private AbstractValue value = null;
	private CacheHandle constant = null;

	protected DeclarationImpl(String name) {
		this.name = name;
	}
	
	public final String getName() {
		return name;
	}

	public Object getValue() {

		if(constant != null) {
			return getConstantValue();
		} else {
			return value.getValue();
		}
	}

	/**
	 * Returns the cached (constant) value. When this is the first time this
	 * operator is evaluated within the constant-scope, it will evaluate
	 * normally and then store the result in the <tt>CacheHandle</tt>.
	 * 
	 * @return
	 */
	private Object getConstantValue() {
		
		if(constant.isEmpty()) {
			constant.setValue( value.getValue() );
		}
		return constant.getValue();
	}
	
	protected void build(DeclareType xmlDeclare) throws ParseException {

		Declarations.parseDefinitions(xmlDeclare);

		final ConstScope.Enum scope;
		if(xmlDeclare.isSetConst()) {
			scope = xmlDeclare.getConst();
		} else {
			scope = ConstScope.NONE;
		}

		
		
		ExpressionImpl ei = Scope.getScope().getExpression();
		final ExpressionContext ctx = ei.getExpressionContext();
		switch(scope.intValue()) {
			case ConstScope.INT_EVAL:
				constant = ctx.createEvalCacheHandle();
				break;
			case ConstScope.INT_EXPR:
				constant = ctx.createExprCacheHandle();
				break;
			case ConstScope.INT_NONE:
				constant = null;
				break;
			default:
				throw new AssertionError("Unsupported const type: " +
						scope.toString());
		}
		value = AbstractValue.parse(xmlDeclare.getValue());
	}

	/**
	 * @param xmlDeclare the XMLBeans stub for this declaration element.
	 * @return
	 * @throws ParseException if the xml declaration element could not be
	 * 	parsed, due to an incorrect or missing java class mapping.
	 */
	public static Declaration parse(DeclareType xmlDeclare)
		throws ParseException {
		
		DeclarationImpl declaration = new DeclarationImpl(
				xmlDeclare.getName());

		declaration.build(xmlDeclare);
		return declaration;
	}
}
