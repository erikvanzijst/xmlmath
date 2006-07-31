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

import org.xmlmath.x10.InputStringType;
import org.xmlmath.x10.InputValueType;

/**
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 05.jun.2006
 */
public class InputStringImpl extends AbstractInputValue implements InputString {

	private String value = null;

	/**
	 * Used by the user application to set the value for this input element
	 * prior to evaluating the expression.
	 */
	public void fromString(String value) {
		this.value = value;
	}
	
	public void setString(String value) {
		this.value = value;
	}
	
	public final Object getValue() {
		return getString();
	}

	public String getString() {
		return value;
	}

	@Override
	protected InputValue getFacade() {

		return new InputString() {
			public void fromString(String value) {
				InputStringImpl.this.fromString(value);
			}
			public String getDescription() {
				return InputStringImpl.this.getDescription();
			}
			public String getName() {
				return InputStringImpl.this.getName();
			}
			public String getString() {
				return InputStringImpl.this.getString();
			}
			public Object getValue() {
				return InputStringImpl.this.getValue();
			}
			public void setString(String value) {
				InputStringImpl.this.setString(value);
			}
		};
	}
	
	@Override
	protected void build(InputValueType xmlObject) throws ParseException {

		InputStringType xml = (InputStringType)xmlObject;
		if(xml.isSetValue()) {
			setString(xml.getValue());
		}
	}
}
