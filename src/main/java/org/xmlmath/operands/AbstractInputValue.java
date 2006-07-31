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

import java.lang.reflect.Method;

import org.xmlmath.x10.InputValueType;

/**
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 05.jun.2006
 */
public abstract class AbstractInputValue implements InputValue {

	private String name = null;
	private String description = null;

	public final String getName() {
		return name;
	}
	
	public final String getDescription() {
		return description;
	}
	
	/**
	 * The instance returned by this method is the instance that is passed to
	 * the <tt>ExpressionImpl</tt>. It can be used to return a wrapper class
	 * that hides the implementation's internal methods for parsing and
	 * building.
	 * 
	 * @return
	 */
	protected InputValue getFacade() {
		
		return this;
	}

	protected abstract void build(InputValueType xmlObject) throws ParseException;

	/**
	 * Instantiates the proper input class and registers it in the parser
	 * scope, so that it can be accessed by referring link-elements.
	 * 
	 * @param xmlObject
	 * @throws ParseException
	 */
	public static void parse(InputValueType xmlObject) throws ParseException {
		
		final AbstractInputValue value;
		try {
			Method method = xmlObject.getClass()
				.getDeclaredMethod("getClassname", new Class[]{});
			String classname = (String)method.invoke(xmlObject, new Object[]{});

			Class clazz = Class.forName(classname);
			value = AbstractInputValue.class.cast(clazz.newInstance());
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new ParseException("Unable to instantiate java class for " +
					"input element: " + xmlObject.toString() +". Does " +
					"the type definition in the schema come with the mandatory " +
					"fixed attribute classname?", e);
		}
	
		value.name = xmlObject.getName();
		value.description = xmlObject.getDescription();
		
		// register as declaration in the parser-scope
		Scope scope = Scope.getScope();
		try {
			scope.setDeclaration(new Declaration() {
				public String getName() {
					return value.getName();
				}
				
				public Object getValue() {
					return value.getValue();
				}
			});
		} catch(DuplicateElementException e) {
			throw new ParseException("Duplicate declaration (" +
					value.getName() + ")");
		}
		
		// register as parameter in the expression object
		scope.getExpression().setParameter(value.getFacade());

		value.build(xmlObject);
	}
}
