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

import org.xmlmath.x10.ValueType;

/**
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 12.mar.2006
 */
public abstract class AbstractValue {

	public abstract Object getValue() throws EvaluationException;

	protected abstract void build(ValueType xmlObject) throws ParseException;
	
	public static AbstractValue parse(ValueType xmlObject) throws ParseException {
		
		AbstractValue value = null;
		String classname = null;
		try {
			Method method = xmlObject.getClass()
				.getDeclaredMethod("getClassname", new Class[]{});
			classname = (String)method.invoke(xmlObject, new Object[]{});

			Class clazz = Class.forName(classname);
			value = AbstractValue.class.cast(clazz.newInstance());
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new ParseException("Unable to instantiate java class for " +
					"constraint element: " + xmlObject.toString() +". Does " +
					"the type definition in the schema come with the mandatory " +
					"fixed attribute classname?", e);
		}
		
		Scope scope = Scope.createScope();
		Scope.push(scope);
		try {
			Declarations.parseDefinitions(xmlObject);

//			Declarations.parseDefinitions(xmlObject.getIncludeList(),
//					xmlObject.getStanzaList(), xmlObject.getDeclareList());
			value.build(xmlObject);
		} finally {
			Scope.pop();
		}
		return value;
	}
}
