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

import org.xmlmath.x10.InputBooleanType;
import org.xmlmath.x10.InputValueType;

/**
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 05.jun.2006
 */
public class InputBooleanImpl extends AbstractInputValue
		implements InputBoolean {

	private Boolean bool = null;

	/**
	 * Used by the user application to set the value for this input element
	 * prior to evaluating the expression.
	 * <P>
	 * Parses the string argument as a boolean. The boolean parsed
	 * represents the value true if the string argument is not null and is
	 * equal, ignoring case, to the string "true".
	 */
	public void fromString(String value) {
		setBoolean(Boolean.parseBoolean(value));
	}
	
	public final Object getValue() {
		return getBoolean();
	}
	
	public Boolean getBoolean() {
		return bool;
	}
	
	public void setBoolean(Boolean bool) {
		this.bool = bool;
	}
	
	@Override
	protected InputValue getFacade() {
		
		return new InputBoolean() {
			public void fromString(String value) {
				InputBooleanImpl.this.fromString(value);
			}
			public Boolean getBoolean() {
				return InputBooleanImpl.this.getBoolean();
			}
			public String getDescription() {
				return InputBooleanImpl.this.getDescription();
			}
			public String getName() {
				return InputBooleanImpl.this.getName();
			}
			public Object getValue() {
				return InputBooleanImpl.this.getValue();
			}
			public void setBoolean(Boolean bool) {
				InputBooleanImpl.this.setBoolean(bool);
			}
		};
	}
	
	@Override
	protected void build(InputValueType xmlObject) throws ParseException {

		InputBooleanType xml = (InputBooleanType)xmlObject;
		if(xml.isSetValue()) {
			setBoolean(xml.getValue());
		}
	}
}
