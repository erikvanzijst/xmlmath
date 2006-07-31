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
import java.util.Collections;
import java.util.List;

import org.xmlmath.Expression;
import org.xmlmath.ExpressionContext;
import org.xmlmath.ExpressionContextImpl;
import org.xmlmath.x10.ExpressionType;
import org.xmlmath.x10.InputValueType;

/**
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 25.may.2006
 */
public class ExpressionImpl implements Expression {

	private AbstractValue value = null;
	private final List<InputValue> parameters = new ArrayList<InputValue>();
	private final ExpressionContextImpl ctx;
	
	private ExpressionImpl() {
		ctx = new ExpressionContextImpl();
	}
	
	public ExpressionContext getExpressionContext() {
		return ctx;
	}
	
	/**
	 * 
	 * @param param
	 * @throws DuplicateElementException if an input parameter under this name
	 * 	already exists.
	 */
	public void setParameter(InputValue param) throws DuplicateElementException {
		
		if(getParameter(param.getName()) == null) {
			parameters.add(param);
		} else {
			throw new DuplicateElementException("Input parameter \"" +
					param.getName() + "\" already exists.");
		}
	}
	
	public InputValue getParameter(String name) {
		
		for(InputValue param : parameters) {
			if(param.getName().equals(name)) {
				return param;
			}
		}
		return null;
	}
	
	public List<InputValue> getParameters() {
		return Collections.unmodifiableList(parameters);
	}
	
	public Object evaluate() throws EvaluationException {

		ctx.beforeEval();
		try {
			return value.getValue();
		} finally {
			ctx.afterEval();
		}
	}
	
	protected void build(ExpressionType xmlExpression) throws ParseException {

		for(InputValueType input : xmlExpression.getInputValueList()) {
			AbstractInputValue.parse(input);
		}
		Declarations.parseDefinitions(xmlExpression);
		
//		Declarations.parseDefinitions(xmlExpression.getIncludeList(),
//				xmlExpression.getStanzaList(), xmlExpression.getDeclareList());
		value = AbstractValue.parse(xmlExpression.getValue());
	}

	/**
	 * Parses the expression. Note that the parsing scope must already be
	 * initialized with a single, empty root scope.
	 * 
	 * @param xmlExpression
	 * @return
	 * @throws ParseException
	 */
	public static ExpressionImpl parse(ExpressionType xmlExpression)
			throws ParseException {
		
		ExpressionImpl expr = new ExpressionImpl();

		try {
			Scope.getScope().setExpression(expr);
		} catch(UnsupportedOperationException e) {
			throw new AssertionError("ExpressionImpl.parse() called " +
					"without an initialized parsing scope.");
		}
		expr.build(xmlExpression);
		
		return expr;
	}
}
