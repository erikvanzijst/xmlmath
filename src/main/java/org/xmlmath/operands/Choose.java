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

import org.xmlmath.x10.ChooseType;
import org.xmlmath.x10.ValueType;

/**
 * This tag allows conditional execution. It consists of three parts: the
 * condition and two expression. If the boolean expression in the if-tag
 * evaluates to true, the expression in the then-tag is evaluated, if it
 * evaluates to false, the expression in the else-tag is evaluated. Note that
 * the else-tag is required.
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 29.may.2006
 */
public class Choose extends AbstractValue {

	private AbstractBooleanValue condition = null;
	private AbstractValue then = null;
	private AbstractValue alternative = null;
	
	@Override
	public Object getValue() throws EvaluationException {
		
		if(condition.getBoolean()) {
			return then.getValue();
		} else {
			return alternative.getValue();
		}
	}

	/**
	 * Configures this instance according to the given XMLBeans object. This
	 * also includes parsing the child nodes or operands recursively.
	 */
	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		ChooseType xml = (ChooseType)xmlObject;

		if(xml.isSetIf() && xml.isSetThen() && xml.isSetElse()) {
			condition = (AbstractBooleanValue)AbstractValue.parse(
					xml.getIf().getAbstractBoolean());
			then = AbstractValue.parse(xml.getThen().getValue());
			alternative = AbstractValue.parse(xml.getElse().getValue());
		} else {
			condition = (AbstractBooleanValue)AbstractValue.parse(
					xml.getAbstractBoolean());
			then = AbstractValue.parse(xml.getValueArray(1));
			alternative = AbstractValue.parse(xml.getValueArray(2));
		}
	}
}
